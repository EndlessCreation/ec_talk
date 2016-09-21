package kr.re.ec.talk.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

import kr.re.ec.talk.util.LogUtil;

/**
 * JDBCProvider Object for mysql
 * @author Kim Taehee
 * @since 140504
 */
public class JDBCProvider {
	//TODO: MUST REMOVE REAL ACCOUNT
	//TODO: make file read
	private static final String DATABASE_USER_ID = "ecserver";
	private static final String DATABASE_USER_PW = "!comdong1";
	private static final String DATABASE_NAME = "ec_talk";
	
	public static final String CHARSET = "utf8"; //for DAO
	
	private Connection connection;
	private String dbName;
	private boolean isOpened = false;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e) { 
			e.printStackTrace(); 
		}
	}

	private boolean open() {
		//LogUtil.v("open called"); 
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/" 
					+ DATABASE_NAME
					+ "?useUnicode=true&characterEncoding=utf-8",
					DATABASE_USER_ID, DATABASE_USER_PW);
		} catch(SQLException e) { 
			LogUtil.e("Cannot getConnection!");
			e.printStackTrace();
			return false;
		}

		isOpened = true;
		return true;
	}

	protected Connection getConnection() {
		open();
		return connection;
	}
}
