package com.imserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;

public class ServerThread implements Runnable {
	// ���嵱ǰ�߳��������Socket
	private Socket socket = null;
	// ���߳��������Socket����Ӧ��������
	BufferedReader br = null;
	private String uid;
	private String sendToUid;
	private OutputStream os;

	public ServerThread(Socket socket) throws IOException {
		this.socket = socket;
		// ��ʼ����Socket��Ӧ��������
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		uid = br.readLine();
	}

	@Override
	public void run() {
		try {
			// ��<uid,socket>��ֵ�Լ��뵽hashmap��
			MyServer.socketMap.put(uid, socket);
			String content = null;
			// ��ȡsendToUid,sendToUid����װ����Ϣ���ݱ��ĵ�һ��

			// ����ѭ�����ϴ�Socket�ж�ȡ�ͻ��˷��͹��������� ,���͵�ָ���û�
			while ((content = readFromClient()) != null) {
				
				System.out.println("contentֵΪ" + content);
				if (content.length()>=10&&content.substring(0, 9).equals("sendToUid")) {
					System.out.println("content.substring(0, 9)" + content.substring(0, 9));
					sendToUid = content.substring(9, 21);
					System.out.println("sendToUidΪ" + sendToUid);
					Socket sendToSocket = MyServer.socketMap.get(sendToUid);
					os = sendToSocket.getOutputStream();
				}
				// ��ȡuid����sendToUid���û�socket
				else {
					os.write((content + "\n").getBytes("utf-8"));
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ȡ�ͻ������ݵķ���
	 * 
	 * @return
	 */
	private String readFromClient() {
		try {
			return br.readLine();
		}
		// �����׽���쳣��������Socket��Ӧ�Ŀͻ����Ѿ��ر�
		catch (Exception e) {
			// ɾ����Socket
			MyServer.socketMap.remove(uid);
			e.printStackTrace();
		}
		return null;
	}
}