package org.lc.activemq.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

public class SendMulticast {
    public static void main(String[] args) throws Throwable {
        // 组播地址
        InetAddress group = InetAddress.getByName("224.5.6.7");
        // 组播端口，同时也是UDP 数据报的发送端口
        int port = 1234;
        MulticastSocket mss = null; 

        // 创建一个用于发送/接收的MulticastSocket组播套接字对象
        mss = new MulticastSocket(port);
        // 创建要发送的组播信息和UDP数据报
        // 携带的数据内容，就是这个activeMQ服务节点用来提供Network Connectors的TCP/IP地址和端口等信息
        String message = "我是一个活动的activeMQ服务节点（节点编号:yyyyyyy），我的可用tcp信息为：XXXXXXXXXX : "; 
        byte[] buffer2 = message.getBytes();
        DatagramPacket dp = new DatagramPacket(buffer2, buffer2.length, group, port);
        // 使用组播套接字joinGroup(),将其加入到一个组播
        mss.joinGroup(group);

        // 开始按照一定的周期向加入到224.0.0.5组播地址的其他ActiveMQ服务节点进行广播
        Thread thread = Thread.currentThread();
        while (!thread.isInterrupted()) {
            // 使用组播套接字的send()方法，将组播数据包对象放入其中，发送组播数据包
            mss.send(dp);
            System.out.println(new Date() + "发起组播：" + message);
            synchronized (SendMulticast.class) {
                SendMulticast.class.wait(5000);
            }
        }

        mss.close();
    }


}
