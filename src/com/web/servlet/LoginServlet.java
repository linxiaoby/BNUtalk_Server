package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Author:linxiaobai 2016/05/01 功能:登录时用户名和密码验证
 */
@WebServlet("/LogServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * 用户名和密码正确返回"success",否则返回"failed"
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("LogServlet");
		PrintWriter os = response.getWriter();
		String uid = request.getParameter("uid");
		String passwd = request.getParameter("passwd");

		// response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		boolean isRight = doCheckDb(uid, passwd);
		if (isRight)
		{
			os.write("success");
			System.out.println("success");
		}
		else
		{
			os.write("failed");
			System.out.println("failed");
		}
		os.flush();
		os.close();
	}

	/**
	 * 查询数据库，验证uid和passwd的正确性
	 * 
	 * @param strUid
	 *            客户端输入的uid
	 * @param strPasswd
	 *            客户端输入的passwd
	 * @return uid和passwd都正确返回true，否则返回false
	 */
	public boolean doCheckDb(String strUid, String strPasswd) {
		String asql = "select* from mds_user_info";
		boolean isRight=false;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bnutalk");
			java.sql.Connection conn = ds.getConnection();
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(asql);

			ResultSet rs = null;
			rs = ps.executeQuery();
			String uid, passwd;
			while (rs.next()) {
				uid = rs.getString("uid");
				passwd = rs.getString("passwd");
				if (strUid.equals(uid) && strPasswd.equals(passwd)) {
					System.out.println("用户名和密码正确");
					isRight=true;
					break;
				}
			}
			rs.close();
			ps.close();
			conn.close();
		
			
		} catch (SQLException se) {
			System.out.println("SQLException: " + se.getMessage());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isRight;
	}
}
