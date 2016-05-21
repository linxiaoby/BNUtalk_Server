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

import org.apache.naming.java.javaURLContextFactory;

import com.web.servlet.GetImageDiskPath;

import sun.misc.BASE64Encoder;

public class AddContactsUtil {
	private List<UserEntity> list;
	public static final int BEFRIEND = 1;

	public AddContactsUtil() {
	}

	public AddContactsUtil(List<UserEntity> list) {
		this.list = list;
	}

	/**
	 * 
	 * @param uid
	 * @param cuid
	 * @return BEFRIEND if both like each other
	 */
	public int rightOperation(String uid, String cuid) {
		int flag = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String checkSql = "select* from mds_add_contact_like where uid=" + cuid + " and like_uid= " + uid;
		String deleteSql = "delete from mds_add_contact_like where uid=" + cuid + " and like_uid= " + uid;
		String saveLikeSql = "insert into mds_add_contact_like(uid,like_uid) values(" + uid + "," + cuid + ")";
		String saveContactSql = "insert into mds_user_contact(uid,contact_uid) values(" + uid + "," + cuid + ")";
		try {
			conn = Common.getConn();
			ps = conn.prepareStatement(checkSql);
			rs = ps.executeQuery();
			if(!rs.next()){
				ps = conn.prepareStatement(saveLikeSql);
				ps.executeUpdate();
			} else {
				ps = conn.prepareStatement(deleteSql);
				ps.executeUpdate();

				ps = conn.prepareStatement(saveContactSql);
				ps.executeUpdate();
				flag = BEFRIEND;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public void deleteLike(String uid, String cuid) {
		boolean isExit = false;
		String sql = "delete from mds_add_contact_like where uid=" + cuid + "and cuid= " + uid;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(Common.DATABESE_NAME);
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param uid
	 * @param cuid
	 */
	public void saveLikeUid(String uid, String cuid) {
		String sql = "insert into mds_add_contact_like(uid,like_uid) values(uid,cuid)";
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(Common.DATABESE_NAME);
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();

			conn.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param uid
	 * @param list
	 */
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
			// java.sql.Connection conn=new GetDbConnection().getConn();

			rs1 = ps1.executeQuery();

			while (rs1.next()) {
				UserEntity userEntity = new UserEntity();
				String xuid = rs1.getString("uid");
				String strPhoto = readDiskImage(xuid);// get photo

				// add to userList
				userEntity.setUid(rs1.getString("uid"));
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
