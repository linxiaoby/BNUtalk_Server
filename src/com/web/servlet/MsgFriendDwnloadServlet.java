package com.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.web.util.User;

import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class MsgFriendDwnloadServlet
 */
@WebServlet(urlPatterns = "/MsgFriendDwnloadServlet")
public class MsgFriendDwnloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	public MsgFriendDwnloadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String uid="201211011063";
		List<User> userList = new ArrayList<User>();
		String uid = request.getParameter("strUid");
		System.out.println("MsgFriendDwnloadServlet is called!");
		response.setContentType("UTF-8");

		// DB operation
		getFriendData(uid,userList);
		String strJson = dataToJson(userList);

		response.setStatus(200);
		PrintWriter out = response.getWriter();
		out.write(strJson);
		out.println(strJson);
		out.flush();
	}

	//
	public void getFriendData(String uid,List<User> userList) {
		String asql = "select friend_uid from mds_user_msg_friend where uid=" + uid;

		try {
			ResultSet rs1 = null;
			ResultSet rs2 = null;

			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bnutalk");
			java.sql.Connection conn = ds.getConnection();
			conn = ds.getConnection();

			PreparedStatement ps1 = conn.prepareStatement(asql);
			rs1 = ps1.executeQuery();

			String fuid;
			while (rs1.next()) {
				fuid = rs1.getString("friend_uid");

				// get photo
				String strPhoto = readDiskImage(fuid);

				// get nick
				String bsql = "select nick from mds_user_info where uid=" + fuid;
				PreparedStatement ps2 = conn.prepareStatement(bsql);
				rs2 = ps2.executeQuery();
				rs2.next();
				String nickname= rs2.getString("nick");
				// add to userList
				User user = new User();
				user.setStrUid(fuid);
				user.setStrNickname(nickname);
				user.setStrPhoto(strPhoto);
				userList.add(user);
			}
		} catch (SQLException se) {
			System.out.println("SQLException: " + se.getMessage());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected String dataToJson(List<User> userList) {
		Gson gson = new Gson();
		System.out.println(gson.toJson(userList));
		return gson.toJson(userList);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
