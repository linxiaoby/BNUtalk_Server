package com.web.util;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.web.util.Common;
public class Common {
	public static final String DATABESE_NAME="java:comp/env/jdbc/bnutalk";
	public static Connection getConn() {
		Connection conn = null;
		try {
			InitialContext ctx;
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(DATABESE_NAME);
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
