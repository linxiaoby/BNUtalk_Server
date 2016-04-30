package com.imserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;

public class ServerThread implements Runnable {
	// 定义当前线程所处理的Socket
	private Socket socket = null;
	// 该线程所处理的Socket所对应的输入流
	BufferedReader br = null;
	private String uid;
	private String sendToUid;
	private OutputStream os;

	public ServerThread(Socket socket) throws IOException {
		this.socket = socket;
		// 初始化该Socket对应的输入流
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		uid = br.readLine();
	}

	@Override
	public void run() {
		try {
			// 把<uid,socket>键值对加入到hashmap中
			MyServer.socketMap.put(uid, socket);
			String content = null;
			// 获取sendToUid,sendToUid被封装在消息数据报的第一行

			// 采用循环不断从Socket中读取客户端发送过来的数据 ,发送到指定用户
			while ((content = readFromClient()) != null) {
				
				System.out.println("content值为" + content);
				if (content.length()>=10&&content.substring(0, 9).equals("sendToUid")) {
					System.out.println("content.substring(0, 9)" + content.substring(0, 9));
					sendToUid = content.substring(9, 21);
					System.out.println("sendToUid为" + sendToUid);
					Socket sendToSocket = MyServer.socketMap.get(sendToUid);
					os = sendToSocket.getOutputStream();
				}
				// 获取uid等于sendToUid的用户socket
				else {
					os.write((content + "\n").getBytes("utf-8"));
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 定义读取客户端数据的方法
	 * 
	 * @return
	 */
	private String readFromClient() {
		try {
			return br.readLine();
		}
		// 如果捕捉到异常，表明该Socket对应的客户端已经关闭
		catch (Exception e) {
			// 删除该Socket
			MyServer.socketMap.remove(uid);
			e.printStackTrace();
		}
		return null;
	}
}