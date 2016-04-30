package com.servlet.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServThread extends Thread {
		//�ͱ��߳���ص�socket
	Socket socket=null;
	//���캯��
	public ServThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket=socket;
	}
	@Override
	public void run() {
		InputStream is=null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		//��ȡ������������ȡ�ͻ��˵���Ϣ
		try {
			is=socket.getInputStream();
			isr=new InputStreamReader(is);
			br=new BufferedReader(isr);
			String info=null;
			while((info=br.readLine())!=null)//ѭ����ȡ�ͻ��˷��͵���Ϣ
			{
				System.out.println("�ͻ��˷��͵���Ϣ��"+info);
			}
			socket.shutdownInput();//�ر�������
			
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
