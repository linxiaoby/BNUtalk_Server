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

public class UserUtil {
	private List<UserEntity> list;

	public UserUtil() {
	}

	public UserUtil(List<UserEntity> list) {
		this.list = list;
	}

	public void getAlllUserData(String uid, List<UserEntity> list) {
		String asql = "select* from mds_user_info where uid!=" + uid;
		try {
			ResultSet rs1 = null;
			ResultSet rs2 = null;

			InitialContext ctx;
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bnutalk");
			java.sql.Connection conn = ds.getConnection();
			PreparedStatement ps1 = conn.prepareStatement(asql);
//			java.sql.Connection conn=new GetDbConnection().getConn();
		
			
			rs1 = ps1.executeQuery();

			while (rs1.next()) {
				UserEntity userEntity = new UserEntity();
				String xuid = rs1.getString("uid");
				String strPhoto = readDiskImage(xuid);// get photo

				// add to userList
				userEntity.setNick(rs1.getString("nick"));
				userEntity.setSex(rs1.getInt("sex"));

				userEntity.setAge(rs1.getInt("age"));
				userEntity.setFaculty(rs1.getString("faculty"));
				userEntity.setLikeLanguage(rs1.getString("like_language"));
				userEntity.setMotherTone(rs1.getString("native_language"));
				userEntity.setUid(rs1.getString("uid"));
				userEntity.setAvatar(strPhoto);

				list.add(userEntity);
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
