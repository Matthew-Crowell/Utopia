package com.smoothstack.matthewcrowell.correctedsingleton;

import java.sql.Connection;

/**
 * Class to demonstrate a corrected version of the
 * provided Singleton class.
 *
 * @author matthew.crowell
 */
public class CorrectedSingleton {

	private static Connection conn = null;
	private static CorrectedSingleton instance = null;
	private static Boolean instanceExists = false;

	/**
	 * Private constructor.
	 */
	private CorrectedSingleton() {
	}

	/**
	 * Getter for SQL connection object.
	 *
	 * @return Connection, connection object for database
	 */
	public static Connection getConn() {
		return conn;
	}

	/**
	 * Setter for SQL connection object.
	 *
	 * @param conn Connection, connection object for database
	 */
	public static void setConn(Connection conn) {
		CorrectedSingleton.conn = conn;
	}

	/**
	 * Get the static instance of the singleton object.
	 *
	 * @return CorrectedSingleton, global instance of object
	 */
	public static CorrectedSingleton getInstance() {
		if (!CorrectedSingleton.instanceExists) {
			synchronized (CorrectedSingleton.class) {
				if (!CorrectedSingleton.instanceExists) {
					instance = new CorrectedSingleton();
					instanceExists = true;
				}
			}
		}
		return instance;
	}

}
