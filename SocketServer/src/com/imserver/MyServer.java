package com.imserver;  
  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;  
  
public class MyServer {  
    // ���屣������Socket�ļ���,�ͻ��˵�uid,Socket�ļ�ֵ��
	public static HashMap<String, Socket> socketMap=new HashMap<String,Socket>();
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();  
    public static int cnt=0;
    public static void main(String[] args) throws IOException {  
        ServerSocket ss = new ServerSocket(7777);  
        System.out.println("�����������ɹ���");  
        System.out.println("�ȴ��͑��˵����ӡ�����");  
        while (true) {  
            // ���д�����������ȴ��û�������  
            Socket socket = ss.accept();  
            cnt++;
            System.out.println("�пͻ������ӽ�����,����"+cnt+"���ͻ�������");  
            socketList.add(socket);
            // ÿ���ͻ������Ӻ�����һ��ServerThread�߳�Ϊ�ÿͻ��˷���  
            new Thread(new ServerThread2(socket)).start();  
        }  
    }  
  
}  