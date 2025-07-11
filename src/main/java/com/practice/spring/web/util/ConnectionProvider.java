package com.practice.spring.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

public class ConnectionProvider {

	private static final ThreadLocal<Connection> TH_CONN = new ThreadLocal<Connection>();
	private static final DataSource ds;
	public static final boolean isStartWebServer = false;
	private static final String url = "jdbc:oracle:thin:@//localhost:1521/SAMPLE";
	private static final String id = "dbuser1";
	private static final String pass = "2vkicn1r";

	static {
		if (isStartWebServer) {
			try {
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				ds = (DataSource) envContext.lookup("jdbc/mydatasource"); // Replace with your JNDI name
			} catch (NamingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} else {
			ds = DataSourceBuilder.create()
					.username(id)
					.password(pass)
					.url(url)
					.build();
		}
	}

	public static Connection getNewConnection() {
		Connection connection = null;
		try {
			if (ds != null) {
				connection = ds.getConnection();
			} else {
				connection = DriverManager.getConnection(url, id, pass);
			}
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return connection;
	}

	public static Connection getConnection() {
		Connection connection = TH_CONN.get();
		if (connection == null) {
			try {
				if (ds != null) {
					connection = ds.getConnection();
				} else {
					connection = DriverManager.getConnection(url, id, pass);
				}
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			TH_CONN.set(connection);
		}
		return connection;
	}

	public static void shutdown() {
		TH_CONN.remove();
	}
}
