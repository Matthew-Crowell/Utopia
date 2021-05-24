package com.smoothstack.matthewcrowell.utopia.service;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(getProperty("driver"));
		Connection conn = DriverManager.getConnection(getProperty("url"), getProperty("dbuser"),
				getProperty("dbpword"));
		conn.setAutoCommit(Boolean.FALSE);
		return conn;
	}

	public Connection getConnection(String connector) throws ClassNotFoundException, SQLException {
		Class.forName(getProperty(connector + "driver"));
		Connection conn = DriverManager.getConnection(getProperty(connector + "url"), getProperty(connector + "dbuser"),
				getProperty(connector + "dbpword"));
		conn.setAutoCommit(Boolean.FALSE);
		return conn;
	}

	public String getProperty(String propertyName) {
		try (InputStream input = new FileInputStream("resources/application.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			return prop.getProperty(propertyName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}