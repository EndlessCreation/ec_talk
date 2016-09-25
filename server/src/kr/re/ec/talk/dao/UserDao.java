package kr.re.ec.talk.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kr.re.ec.talk.dto.User;
import kr.re.ec.talk.jdbc.JDBCProvider;
import kr.re.ec.talk.util.LogUtil;

/**
 * User Data Access Object
 * @author Taehee Kim 2016-09-16
 */
public class UserDao extends JDBCProvider {
	/** for singleton */
	private static UserDao instance = null;

	public static final String TABLE_NAME = "user";

	//columns 
	/** column index: PK, autoincrement */
	private static final String COL_ID = "id";
	private static final String COL_TOKEN = "token";
	private static final String COL_NICKNAME = "nickname";
	private static final String COL_DEVICE_ID = "deviceId";

	/** for singleton */
	private UserDao() {
		super();
	}

	/** for singleton */
	public static UserDao getInstance() {
		if(instance==null) {
			instance = new UserDao();
		}
		return instance;
	}

	/**
	 * Create table if not exists.
	 * @return success
	 */
	public boolean createTableIfNotExists() { 
		Connection c = null; 
		Statement stmt = null;
		try {
			c = getConnection();
			stmt = c.createStatement();

			String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" 
					+ COL_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY, " 
					+ COL_TOKEN + " VARCHAR(36) NOT NULL UNIQUE, " 
					+ COL_NICKNAME + " VARCHAR(200) NOT NULL UNIQUE, " 
					+ COL_DEVICE_ID + " VARCHAR(200)) " 
					+ "DEFAULT CHARACTER SET = " + CHARSET + ";"; 
			//this query depends on mysql
			LogUtil.v("query: " + query);

			stmt.executeUpdate(query);
		} catch (SQLException e) {
			LogUtil.e(e.getMessage());
			return false;
		} finally {
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return true;

	}

	/**
	 * Drop table
	 * @return success
	 */
	public boolean dropTable() { 
		Connection c = null; 
		Statement stmt = null;
		try {
			c = getConnection();
			stmt = c.createStatement();

			String query = "DROP TABLE " + TABLE_NAME + ";";
			LogUtil.v("query: " + query);
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			LogUtil.e(e.getMessage());
			return false;
		} finally {
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return true;
	}

	//find
	/**
	 * find all users
	 * @return ArrayList<User> 
	 * @throws SQLException 
	 */
	public ArrayList<User> findAllUsers() throws SQLException { 
		ArrayList<User> users = new ArrayList<>();

		Connection c = null; 
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "SELECT " 
					+ COL_ID + ", "
					+ COL_TOKEN + ", " 
					+ COL_NICKNAME + ", " 
					+ COL_DEVICE_ID
					+ " FROM " + TABLE_NAME 
					+ ";"; 
			LogUtil.v("query: " + query);
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				User user = new User(
						rs.getInt(COL_ID),
						rs.getString(COL_TOKEN),
						rs.getString(COL_NICKNAME),
						rs.getString(COL_DEVICE_ID)
						);
				users.add(user);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) 		try {rs.close();	} catch(Exception e){}
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return users;
	}

	/**
	 * get all device id except for my token
	 * @return ArrayList<String> 
	 * @throws SQLException 
	 */
	public ArrayList<String> findAllDeviceIdsExceptForSender(String senderToken) throws SQLException { 
		ArrayList<String> deviceIds = new ArrayList<>();

		Connection c = null; 
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "SELECT " 
					+ COL_DEVICE_ID
					+ " FROM " + TABLE_NAME
					+ " WHERE " + COL_TOKEN + " <> '" + senderToken //not sender 
					+ "' AND " + COL_DEVICE_ID + " <> ''" //not empty 
					+ ";"; 
			LogUtil.v("query: " + query);
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				//not empty or not mine
				deviceIds.add(rs.getString(COL_DEVICE_ID));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) 		try {rs.close();	} catch(Exception e){}
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return deviceIds;
	}

	/**
	 * Validation user by token
	 * @throws SQLException 
	 */
	public boolean isValidUserByToken(String token) throws SQLException { 
		boolean isValid = false;

		Connection c = null; 
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "SELECT " 
					+ " COUNT(" + COL_TOKEN + ")"
					+ " FROM " + TABLE_NAME 
					+ " WHERE " + COL_TOKEN + "='" + token + "'"
					+ ";"; 
			LogUtil.v("query: " + query);
			rs = stmt.executeQuery(query);
			int countResult = 0;
			if(rs.next()) {
				//if correct token exist, result should be 1
				countResult = rs.getInt(1);
				LogUtil.v("countResult: " + countResult);
			}

			if(countResult == 1) {
				isValid = true;
			} else {
				isValid = false;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) 		try {rs.close();	} catch(Exception e){}
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return isValid;
	}

	/**
	 * find user by Token
	 * @throws SQLException
	 * @return if invalid token, return null. 
	 */
	public User findUserByToken(String token) throws SQLException { 
		User user = null;

		Connection c = null; 
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "SELECT " 
					+ COL_ID + ", "
					+ COL_TOKEN + ", " 
					+ COL_NICKNAME + ", " 
					+ COL_DEVICE_ID
					+ " FROM " + TABLE_NAME 
					+ " WHERE " + COL_TOKEN + "='" + token + "'"
					+ ";"; 
			LogUtil.v("query: " + query);
			rs = stmt.executeQuery(query);
			if(rs.next()) { //can return only 1 user (cuz of unique token)
				user = new User(
						rs.getInt(COL_ID),
						rs.getString(COL_TOKEN),
						rs.getString(COL_NICKNAME),
						rs.getString(COL_DEVICE_ID)
						);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) 		try {rs.close();	} catch(Exception e){}
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return user;
	}

	//insert
	/** 
	 * insert a row. except for id. (autoincrement)
	 * @param User
	 * @return the inserted row count.
	 * @throws SQLException 
	 */
	public int insertNewUser(User user) throws SQLException { 
		int result = 0;

		Connection c = null; 
		Statement stmt = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "INSERT INTO " + TABLE_NAME 
					+ " (" + COL_TOKEN + ", " + COL_NICKNAME 
					+ ", " + COL_DEVICE_ID + ")" + " VALUES ('" 
					+ user.getToken() + "','" 
					+ user.getNickname() + "','"
					+ user.getDeviceId() + "');"; //for boolean
			LogUtil.v("query: " + query);
			result=stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw e;
		} finally {
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return result;
	}

	//update
	/** 
	 * update deviceId by id
	 * @param id id
	 * @param deviceId deviceId to update
	 * @return the updated row count.
	 * @throws SQLException 
	 */
	public int updateDeviceIdById(long id, String deviceId) throws SQLException { 
		int result = 0;

		Connection c = null; 
		Statement stmt = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "UPDATE " + TABLE_NAME
					+ " SET " + COL_DEVICE_ID + "='" + deviceId + "'"
					+ " WHERE " + COL_ID + "='" + id + "';";
			LogUtil.v("query: " + query);
			result=stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw e;
		} finally {
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return result;
	}
}
