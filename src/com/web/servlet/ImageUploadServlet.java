package com.web.servlet;  
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
        
       /*图片上传至数据库,String类型存成Blob形式*/
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
        /*图片解码并存到磁盘中*/
        try {  
            // 对base64数据进行解码 生成 字节数组，不能直接用Base64.decode（）；进行解密  
            byte[] photoimg = new BASE64Decoder().decodeBuffer(strPhoto);  
            for (int i = 0; i < photoimg.length; ++i) {  
                if (photoimg[i] < 0) {  
                    // 调整异常数据  
                    photoimg[i] += 256;  
                }  
            }  
            //图片存入磁盘F:\\BNUtalkDB\\userProfileImage，以uid.png命名,uid是唯一，于是数据库中连路径都不用存了，不过为了保险，还是把路径存下来
            // byte[] photoimg = Base64.decode(photo);//此处不能用Base64.decode（）方法解密，我调试时用此方法每次解密出的数据都比原数据大  所以用上面的函数进行解密，在网上直接拷贝的，花了好几个小时才找到这个错误（菜鸟不容易啊）  
            System.out.println("图片的大小：" + photoimg.length);  
            String strImageName=strUid+".png";
            String strImagePath=new GetImageDiskPath().getImageDiskPath();
            File file = new File(strImagePath, strImageName);  //图片存入磁盘
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