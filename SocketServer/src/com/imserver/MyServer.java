package com.imserver;  
  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;  
  
public class MyServer {  
    // 定义保存所有Socket的集合,客户端的uid,Socket的键值对
	public static HashMap<String, Socket> socketMap=new HashMap<String,Socket>();
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();  
    public static int cnt=0;
    public static void main(String[] args) throws IOException {  
        ServerSocket ss = new ServerSocket(7777);  
        System.out.println("服务器创建成功！");  
        System.out.println("等待客戶端的连接。。。");  
        while (true) {  
            // 此行代码会阻塞，等待用户的连接  
            Socket socket = ss.accept();  
            cnt++;
            System.out.println("有客户端连接进来！,共有"+cnt+"个客户端在线");  
            socketList.add(socket);
            // 每当客户端连接后启动一条ServerThread线程为该客户端服务  
            new Thread(new ServerThread(socket)).start();  
        }  
    }  
  
}  