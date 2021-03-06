<<<<<<< HEAD
package com.imserver;
/**
 * Created On:2016/04/30
 * Author:linxiaobai 
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Iterator;
import java.lang.String;
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
			String fromUid=null;
			// 获取sendToUid,sendToUid被封装在消息数据报的第一行

			// 采用循环不断从Socket中读取客户端发送过来的数据 ,发送到指定用户
			while ((content = readFromClient()) != null) {

				System.out.println("content值为" + content);
				if (content.length() >= 10 && content.substring(0, 9).equals("sendToUid")) {
					System.out.println("content.substring(0, 9)" + content.substring(0, 9));
					
					sendToUid = content.substring(9, 21);
					System.out.println("sendToUid为" + sendToUid);
					
					Socket sendToSocket = MyServer.socketMap.get(sendToUid);
					if (sendToSocket != null)
					{
						os = sendToSocket.getOutputStream();
						if(os!=null)
						{
							
							
						}
					}
					else//if friend is not online,save messge into database
					{
						
					}
				}
				// 获取uid等于sendToUid的用户socket
				else {
					if (os != null) {
						System.out.println("服务端将消息：" + content + " 写进输出流");
						os.write((content + "\n").getBytes("utf-8"));
						os.flush();
					}
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
			MyServer.cnt--;
			System.out.println("客户端" + uid + "已掉线");
			System.out.println("还有" + MyServer.cnt + "个客户端在线");
			e.printStackTrace();
		}
		return null;
	}
=======
package com.imserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Iterator;

import com.bnutalk.socket.MsgEntity;

/*
 * Author:linxiaoby 2016/04/30 
 * 功能：循环读取客户端发送来的聊天消息，并发送给对方好友
 */
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
			System.out.println("客户端的uid是" + uid);
			String fromUid = uid;
			String content = null;
			InputStream isAll = socket.getInputStream();
			// 采用循环不断从Socket中读取客户端发送过来的数据 ,发送到指定用户
			byte[] b = new byte[1024];// 暂定1000个字节，超过要出事儿，记得回来改
			while (true) {
				int n=isAll.read(b);
				if (n>0){
					//服务端接收到一条消息
					String tmp=new String(b);
					System.out.println(uid + "发送的消息是" +tmp);
					
					MsgEntity msgEtity = (MsgEntity) MsgEntity.ByteToObject(b);
					sendToUid = msgEtity.getSendToUid();
					Socket sendToSocket = MyServer.socketMap.get(sendToUid);

					if (sendToSocket != null) {
						os = sendToSocket.getOutputStream();
						os.write(b);
						os.flush();
					} else
						System.out.println("对方好友不在线，消息不能发送");
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/*
	 * run方法 读取客户端消息：先读取消息报头，再一次性读取消息正文
	 * br按行读取，用户读取消息报头,isAll用于一次性读取消息正文，消息正文可以包含回车和换行
	 */
	/*
	 * @Override public void run() { try { // 把<uid,socket>键值对加入到hashmap中
	 * MyServer.socketMap.put(uid, socket); System.out.println("客户端的uid是"+uid);
	 * String fromUid=uid; String content = null; // 采用循环不断从Socket中读取客户端发送过来的数据
	 * ,发送到指定用户 while (true) { content = br.readLine(); if (content != null) {
	 * //get the sendToUid and date sendToUid=br.readLine(); String
	 * date=br.readLine();
	 * 
	 * // System.out.println("content.substring(0, 9)" + content.substring(0,
	 * 9)); // sendToUid = content.substring(9, 21); //
	 * System.out.println("uid是" + uid + "	sendToUid为" + sendToUid);
	 * System.out.println("fromUid是" + uid + "	sendToUid为" +
	 * sendToUid+"date是"+date);
	 * 
	 * Socket sendToSocket = MyServer.socketMap.get(sendToUid); if (sendToSocket
	 * != null) { os = sendToSocket.getOutputStream(); InputStream isAll =
	 * socket.getInputStream();
	 * 
	 * os.write((fromUid+"\r\n").getBytes());
	 * os.write((sendToUid+"\r\n").getBytes());
	 * os.write((date+"\r\n").getBytes());
	 * 
	 * byte[] b = new byte[1000];//暂定1000个字节，超过要出事儿，记得回来改 isAll.read(b);
	 * System.out.println(uid + "发送的消息是" + new String(b)); os.write(b);
	 * os.flush(); } else System.out.println("对方好友不在线，消息不能发送"); } } } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */

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
			MyServer.cnt--;
			System.out.println("客户端" + uid + "已掉线");
			System.out.println("还有" + MyServer.cnt + "个客户端在线");
			e.printStackTrace();
		}
		return null;
	}
>>>>>>> origin/master
}