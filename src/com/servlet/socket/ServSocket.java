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
			System.out.println("服务器"+portnum+"端口已自动开启");
			Socket socket=null;
			while(true)//循环监听客户端的连接
			{
				//调用accept()方法开始监听，等待客户端的连接
				socket=serverSocket.accept();
				ServThread  serverThread=new ServThread(socket);
				serverThread.start();	
				InetAddress address=socket.getInetAddress();
				System.out.println("当前客户端的IP是"+address.getHostAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
