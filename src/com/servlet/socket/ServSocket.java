package com.servlet.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.servlet.socket.ServThread;

public class ServSocket {
//	public ServSocket() {
//		// TODO Auto-generated constructor stub
//	}
	public void openport(){
		try {
			int portnum=8211;
			ServerSocket serverSocket=new ServerSocket(portnum);
			System.out.println("������"+portnum+"�˿����Զ�����");
			Socket socket=null;
			while(true)//ѭ�������ͻ��˵�����
			{
				//����accept()������ʼ�������ȴ��ͻ��˵�����
				socket=serverSocket.accept();
				ServThread  serverThread=new ServThread(socket);
				serverThread.start();	
				InetAddress address=socket.getInetAddress();
				System.out.println("��ǰ�ͻ��˵�IP��"+address.getHostAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
