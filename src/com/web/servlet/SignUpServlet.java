package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.catalina.Context;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpServlet() {
		System.out.println("SignUpServlet constructer");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mail = request.getParameter("mailAdress");
		String passwd = request.getParameter("passwd");
		PrintWriter out = response.getWriter();
		System.out.println("username" + "," + mail);
		int rs ;
		String asql = "insert into mds_user_info(uid,mail,passwd) values(3,?,?)";
		String bsql="select count(*) from mds_user_info";
		//String sql = "select* from bnutalk";
//		DbConnection dbConnection = null;
//		Connection conn=dbConnection.getConn();
//		
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bnutalk");
			java.sql.Connection conn =ds.getConnection();
			conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement(asql);
			ps.setString(1, mail);
			ps.setString(2, passwd);
			rs= ps.executeUpdate();
		} catch (SQLException se) {

			System.out.println("SQLException: " + se.getMessage());

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
