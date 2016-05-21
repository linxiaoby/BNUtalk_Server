package com.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.web.servlet.GetImageDiskPath;

import sun.misc.BASE64Encoder;
/**
 * Created on 2016-05-21
 * @author 王琳—PC
 *
 */
public class ContactUtil {
	private List<ContactEntity> list;

	public ContactUtil() {
	}

	public ContactUtil(List<ContactEntity> list) {
		this.list = list;
	}

	public void getContact(String uid, List<ContactEntity> list) {
		String asql = "select* from mds_user_contact where uid="+uid;
		
		try {
			ResultSet rs1 = null;
			ResultSet rs2 = null;

			InitialContext ctx;
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bnutalk");
			java.sql.Connection conn = ds.getConnection();
			
			PreparedStatement ps1 = conn.prepareStatement(asql);
			rs1 = ps1.executeQuery();

			while (rs1.next()) {
				ContactEntity cEntity = new ContactEntity();
				String cuid = rs1.getString("contact_uid");
				cEntity.setUid(cuid);
				
				String strPhoto = readDiskImage(cuid);// get photo
				cEntity.setAvatar(strPhoto);
				
				String bsql = "select nick,nationality from mds_user_info where uid="+cuid;
				PreparedStatement ps2 = conn.prepareStatement(bsql);
				rs2 = ps2.executeQuery();
				rs2.next();
				cEntity.setNick(rs2.getString("nick"));
				cEntity.setNationality(rs2.getString("nationality"));

				list.add(cEntity);
			}
			rs1.close();
			ps1.close();
			conn.close();

		} catch (SQLException se) {
			System.out.println("SQLException: " + se.getMessage());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 从磁盘中获取指定用户的头像，如果成功执行，返回String类型
	 * 
	 * @param uid用户uid
	 * @return 头像的String类型
	 */
	protected String readDiskImage(String uid) {
		String strPhoto = null;
		String imageDiskPath = new GetImageDiskPath().getImageDiskPath();
		String imageName = uid + ".png";
		File file = new File(imageDiskPath, imageName);
		try {
			FileInputStream in = new FileInputStream(file);
			int fileLen = in.available();
			byte[] b = new byte[fileLen];
			in.read(b);
			System.out.println("图片的长度是" + fileLen);
			in.close();
			strPhoto = new BASE64Encoder().encodeBuffer(b);
			System.out.println(strPhoto);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strPhoto;
	}

}
