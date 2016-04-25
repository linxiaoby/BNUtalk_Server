package com.servlet.ui;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.catalina.Context;

import com.mysql.jdbc.Connection;
public class DbConnection {
	private Connection conn=null;
	String dbdir="java:comp/env/jdbc/bnutalk";
	public Connection getConn()
	{
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dbdir);
			conn =(Connection) ds.getConnection();
		} catch (SQLException se) {
			System.out.println("SQLException: " + se.getMessage());
		} catch (NamingException ne) {
			System.out.println("NamingException: " + ne.getMessage());

		}
		return conn;
	}
}
