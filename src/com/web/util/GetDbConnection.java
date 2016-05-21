package com.web.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.catalina.Context;

import com.mysql.jdbc.Connection;

public class GetDbConnection {
	private PreparedStatement ps = null;

	private static final String dbdir = "java:comp/env/jdbc/bnutalk";

	public static java.sql.Connection  getConn() {
		java.sql.Connection  conn = null;
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bnutalk");
			conn = (Connection) ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
