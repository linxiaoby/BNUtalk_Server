package com.web.servlet;

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

/**
 * Servlet implementation class PersInfoUploadServlet
 */
@WebServlet("/PersInfoUploadServlet")
public class PersInfoUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PersInfoUploadServlet() {
		System.out.println("PersInfoUploadServlet construct is called");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("PersInfoUploadServlet doGet is called");
		String strUid = request.getParameter("strUid");
		String strPasswd = request.getParameter("strPasswd");
		int sex=Integer.valueOf(request.getParameter("sex"));
		String strNickName = request.getParameter("strNickName");
		int age = Integer.valueOf(request.getParameter("age"));
		String strFaculty = request.getParameter("strFaculty");
		String strNationality = request.getParameter("strNationality");
		String strMother = request.getParameter("strMother");
		String strLike = request.getParameter("strLike");

		System.out.println(strUid + " " + strPasswd + " " + sex + " " + strNickName + " " + age + " " + strFaculty
				+ " " + strNationality + " " + strMother + " " + strLike);

		// 数据信息存到数据库 insert into 表名(列名) values(值1，值2...)
		String asql = "insert into mds_user_info(uid,passwd,sex,nick,age,faculty,nationality,native_language,like_language)"
				+ " values(?,?,?,?,?,?,?,?,?)";
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bnutalk");
			java.sql.Connection conn = ds.getConnection();
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(asql);
			ps.setString(1, strUid);
			ps.setString(2, strPasswd);
			ps.setInt(3, sex);
			ps.setString(4, strNickName);
			ps.setInt(5, age);
			ps.setString(6, strFaculty);
			ps.setString(7, strNationality);
			ps.setString(8, strMother);
			ps.setString(9, strLike);
			ps.executeUpdate();
		} catch (SQLException se) {

			System.out.println("SQLException: " + se.getMessage());

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
