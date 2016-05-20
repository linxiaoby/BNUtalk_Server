package com.imserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Iterator;

/*
 * Author:linxiaoby 2016/04/30 
 * ���ܣ�ѭ����ȡ�ͻ��˷�������������Ϣ�������͸��Է�����
 */
public class ServerThread2 implements Runnable {
	// ���嵱ǰ�߳��������Socket
	private Socket socket = null;
	// ���߳��������Socket����Ӧ��������
	BufferedReader br = null;
	private String uid;
	private String sendToUid;
	private OutputStream os;

	public ServerThread2(Socket socket) throws IOException {
		this.socket = socket;
		// ��ʼ����Socket��Ӧ��������
		br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		uid = br.readLine();
	}
	
/*run����
 *��ȡ�ͻ�����Ϣ���ȶ�ȡ��Ϣ��ͷ����һ���Զ�ȡ��Ϣ����
 *br���ж�ȡ���û���ȡ��Ϣ��ͷ,isAll����һ���Զ�ȡ��Ϣ���ģ���Ϣ���Ŀ��԰����س��ͻ���
 */
	@Override
	public void run() {
		try {
			// ��<uid,socket>��ֵ�Լ��뵽hashmap��
			MyServer.socketMap.put(uid, socket);
			System.out.println("�ͻ��˵�uid��"+uid);
			String fromUid=uid;
			String content = null;
			// ����ѭ�����ϴ�Socket�ж�ȡ�ͻ��˷��͹��������� ,���͵�ָ���û�
			while (true) {
				content = br.readLine();
				if (content != null) {
					//get the sendToUid and date
					sendToUid=br.readLine();
					String date=br.readLine();
					
//					System.out.println("content.substring(0, 9)" + content.substring(0, 9));
//					sendToUid = content.substring(9, 21);
//					System.out.println("uid��" + uid + "	sendToUidΪ" + sendToUid);
					System.out.println("fromUid��" + uid + "	sendToUidΪ" + sendToUid+"date��"+date);
					
					Socket sendToSocket = MyServer.socketMap.get(sendToUid);
					if (sendToSocket != null) {
						os = sendToSocket.getOutputStream();
						InputStream isAll = socket.getInputStream();
						
						os.write((fromUid+"\r\n").getBytes());
						os.write((sendToUid+"\r\n").getBytes());
						os.write((date+"\r\n").getBytes());
						
						byte[] b = new byte[1000];//�ݶ�1000���ֽڣ�����Ҫ���¶����ǵû�����
						isAll.read(b);
						System.out.println(uid + "���͵���Ϣ��" + new String(b));
						os.write(b);
						os.flush();
					} else
						System.out.println("�Է����Ѳ����ߣ���Ϣ���ܷ���");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ȡ�ͻ������ݵķ���
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
			MyServer.cnt--;
			System.out.println("�ͻ���" + uid + "�ѵ���");
			System.out.println("����" + MyServer.cnt + "���ͻ�������");
			e.printStackTrace();
		}
		return null;
	}
}