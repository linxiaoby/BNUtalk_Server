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
			String fromUid=null;
			// ��ȡsendToUid,sendToUid����װ����Ϣ���ݱ��ĵ�һ��

			// ����ѭ�����ϴ�Socket�ж�ȡ�ͻ��˷��͹��������� ,���͵�ָ���û�
			while ((content = readFromClient()) != null) {

				System.out.println("contentֵΪ" + content);
				if (content.length() >= 10 && content.substring(0, 9).equals("sendToUid")) {
					System.out.println("content.substring(0, 9)" + content.substring(0, 9));
					
					sendToUid = content.substring(9, 21);
					System.out.println("sendToUidΪ" + sendToUid);
					
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
				// ��ȡuid����sendToUid���û�socket
				else {
					if (os != null) {
						System.out.println("����˽���Ϣ��" + content + " д�������");
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
			MyServer.cnt--;
			System.out.println("�ͻ���" + uid + "�ѵ���");
			System.out.println("����" + MyServer.cnt + "���ͻ�������");
			e.printStackTrace();
		}
		return null;
	}
}