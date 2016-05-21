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
 * Author:linxiaobai 2016/05/01 ����:��¼ʱ�û�����������֤
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
	 * �û�����������ȷ����"success",���򷵻�"failed"
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
	 * ��ѯ���ݿ⣬��֤uid��passwd����ȷ��
	 * 
	 * @param strUid
	 *            �ͻ��������uid
	 * @param strPasswd
	 *            �ͻ��������passwd
	 * @return uid��passwd����ȷ����true�����򷵻�false
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
					System.out.println("�û�����������ȷ");
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
