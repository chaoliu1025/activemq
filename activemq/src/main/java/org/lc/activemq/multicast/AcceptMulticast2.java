package org.lc.activemq.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AcceptMulticast2 {
	public static void main(String[] args) throws Throwable {
        // 建立组播套接字，并加入分组
        MulticastSocket multicastSocket = new MulticastSocket(19999);
        // 注意，组播地址和端口必须和发送者的一直，才能加入正确的组
        InetAddress ad = InetAddress.getByName("239.0.0.5");
        multicastSocket.joinGroup(ad);

        // 准备接收可能的组播信号
        byte[] datas = new byte[2048];
        DatagramPacket data = new DatagramPacket(datas, 2048 ,ad , 19999);
        Thread thread = Thread.currentThread();

        // 开始接收组播信息，并打印出来
        System.out.println(".....开始接收组播信息2.....");
        while(!thread.isInterrupted()) {
            multicastSocket.receive(data);
            int leng = data.getLength();
            System.out.println(new String(data.getData() , 0 , leng , "UTF-8"));
        }

        multicastSocket.close();
    }

}
