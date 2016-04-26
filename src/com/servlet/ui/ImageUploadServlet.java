package com.servlet.ui;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import sun.misc.BASE64Decoder;  
@WebServlet("/ImageUploadServlet")  
public class ImageUploadServlet extends HttpServlet {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ImageUploadServlet() {
		  System.out.println("ImageUploadServletaaaa construct"); 
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        request.setCharacterEncoding("utf-8");  
        response.setCharacterEncoding("utf-8");  
        response.setContentType("text/html");  
        String strUid=request.getParameter("strUid");  
        String strPhoto= request.getParameter("strPhoto");  
        
       /*ͼƬ�ϴ������ݿ�,String���ʹ��Blob��ʽ*/
//    	String asql = "insert into mds_user_image(uid,image) values(?,?)";
//        try {
//			InitialContext ctx = new InitialContext();
//			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bnutalk");
//			java.sql.Connection conn = ds.getConnection();
//			conn = ds.getConnection();
//			PreparedStatement ps = conn.prepareStatement(asql);
//			ps.setString(1, strUid);
//			ps.setBlob(2, inputStream);
//			ps.setString(2, strPhoto);
//			ps.executeUpdate();
//		} catch (SQLException se) {
//			System.out.println("SQLException: " + se.getMessage());
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
        /*ͼƬ���벢�浽������*/
        try {  
            // ��base64���ݽ��н��� ���� �ֽ����飬����ֱ����Base64.decode���������н���  
            byte[] photoimg = new BASE64Decoder().decodeBuffer(strPhoto);  
            for (int i = 0; i < photoimg.length; ++i) {  
                if (photoimg[i] < 0) {  
                    // �����쳣����  
                    photoimg[i] += 256;  
                }  
            }  
            //ͼƬ�������F:\\BNUtalkDB\\userProfileImage����uid.png����,uid��Ψһ���������ݿ�����·�������ô��ˣ�����Ϊ�˱��գ����ǰ�·��������
            // byte[] photoimg = Base64.decode(photo);//�˴�������Base64.decode�����������ܣ��ҵ���ʱ�ô˷���ÿ�ν��ܳ������ݶ���ԭ���ݴ�  ����������ĺ������н��ܣ�������ֱ�ӿ����ģ����˺ü���Сʱ���ҵ�������󣨲������װ���  
            System.out.println("ͼƬ�Ĵ�С��" + photoimg.length);  
            String strImageName=strUid+".png";
            String strImagePath=new GetImageDiskPath().getImageDiskPath();
            File file = new File(strImagePath, strImageName);  //ͼƬ�������
//            File filename = new File("e:\\name.txt");  
//            if (!filename.exists()) {  
//                filename.createNewFile();  
//            }  
            if (!file.exists()) {  
                file.createNewFile();  
            }  
            FileOutputStream out = new FileOutputStream(file);  
//            FileOutputStream out1 = new FileOutputStream(filename);  
//            out1.write(strUid.getBytes());  
            out.write(photoimg);  
            out.flush();  
            out.close();  
//            out1.flush();  
//            out1.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
}