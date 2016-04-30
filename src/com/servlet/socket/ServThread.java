package com.servlet.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServThread extends Thread {
		//和本线程相关的socket
	Socket socket=null;
	//构造函数
	public ServThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket=socket;
	}
	@Override
	public void run() {
		InputStream is=null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		//获取输入流，并读取客户端的信息
		try {
			is=socket.getInputStream();
			isr=new InputStreamReader(is);
			br=new BufferedReader(isr);
			String info=null;
			while((info=br.readLine())!=null)//循环读取客户端发送的信息
			{
				System.out.println("客户端发送的信息是"+info);
			}
			socket.shutdownInput();//关闭输入流
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(br!=null)
					br.close();
				if(isr!=null)
					isr.close();
				if(is!=null)
					is.close();
				if(socket!=null)
					socket.close();
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
