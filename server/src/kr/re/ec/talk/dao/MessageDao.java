package kr.re.ec.talk.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kr.re.ec.talk.dto.Message;
import kr.re.ec.talk.jdbc.JDBCProvider;
import kr.re.ec.talk.util.LogUtil;

/**
 * Message Data Access Object
 * @author Taehee Kim 2016-09-16
 */
public class MessageDao extends JDBCProvider {
	/** for singleton */
	private static MessageDao instance = null;

	public static final String TABLE_NAME = "message";

	//columns 
	/** column index: PK, autoincrement */
	private static final String COL_ID = "id";
	private static final String COL_SENDER_ID = "senderId";
	private static final String COL_SENDER_NICKNAME = "senderNickname";
	private static final String COL_SENDER_TOKEN = "senderToken";
	private static final String COL_SEND_DATETIME = "sendDatetime";
	private static final String COL_RECEIVER_TOKEN = "receiverToken";
	private static final String COL_CONTENTS = "contents";
	private static final String COL_STATE = "state";

	/** for singleton */
	private MessageDao() {
		super();
	}

	/** for singleton */
	public static MessageDao getInstance() {
		if(instance==null) {
			instance = new MessageDao();
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
					+ COL_ID + " BIGINT AUTO_INCREMENT PRIMARY KEY, " 
					+ COL_SENDER_ID + " INTEGER NOT NULL, " 
					+ COL_SENDER_NICKNAME + " VARCHAR(200) NOT NULL, " 
					+ COL_SENDER_TOKEN + " VARCHAR(36) NOT NULL, "
					+ COL_SEND_DATETIME + " VARCHAR(30) NOT NULL, "
					+ COL_RECEIVER_TOKEN + " VARCHAR(36) NOT NULL, "
					+ COL_CONTENTS + " VARCHAR(2000) NOT NULL, "
					+ COL_STATE + " INTEGER NOT NULL) "
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
	 * find top 100 messages.
	 * @return ArrayList<Message> 
	 * @throws SQLException 
	 */
	public ArrayList<Message> findTop100Messages() throws SQLException { 
		ArrayList<Message> msgs = new ArrayList<>();

		Connection c = null; 
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "SELECT " 
					+ COL_ID + ", "
					+ COL_SENDER_ID + ", " 
					+ COL_SENDER_NICKNAME + ", " 
					+ COL_SENDER_TOKEN + ", "
					+ COL_SEND_DATETIME + ", "
					+ COL_RECEIVER_TOKEN + ", "
					+ COL_CONTENTS + ", "
					+ COL_STATE
					+ " FROM " + TABLE_NAME 
					+ " ORDER BY " + COL_SEND_DATETIME + " DESC"
					+ " LIMIT 0, 100"
					+ ";"; 
			LogUtil.v("query: " + query);
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				Message m = new Message(
						rs.getLong(COL_ID),
						rs.getInt(COL_SENDER_ID),
						rs.getString(COL_SENDER_NICKNAME),
						rs.getString(COL_SENDER_TOKEN),
						rs.getString(COL_SEND_DATETIME),
						rs.getString(COL_RECEIVER_TOKEN),
						rs.getString(COL_CONTENTS),
						rs.getInt(COL_STATE)
						);
				msgs.add(m);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) 		try {rs.close();	} catch(Exception e){}
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return msgs;
	}

	/**
	 * find unsent messages to client by receiver's token.
	 * @return ArrayList<Message> 
	 * @throws SQLException 
	 */
	public ArrayList<Message> findUnsentMessagesByReceiverToken(String token) throws SQLException { 
		ArrayList<Message> msgs = new ArrayList<>();

		Connection c = null; 
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "SELECT " 
					+ COL_ID + ", "
					+ COL_SENDER_ID + ", " 
					+ COL_SENDER_NICKNAME + ", " 
					+ COL_SENDER_TOKEN + ", "
					+ COL_SEND_DATETIME + ", "
					+ COL_RECEIVER_TOKEN + ", "
					+ COL_CONTENTS + ", "
					+ COL_STATE
					+ " FROM " + TABLE_NAME 
					+ " WHERE " + COL_RECEIVER_TOKEN + "='" + token + "' "
					+ " AND " + COL_STATE + "='" + Message.STATE_NOT_SENT_TO_CLIENT + "' "
					+ " ORDER BY " + COL_SEND_DATETIME + " DESC"
					+ ";"; 
			LogUtil.v("query: " + query);
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				Message m = new Message(
						rs.getLong(COL_ID),
						rs.getInt(COL_SENDER_ID),
						rs.getString(COL_SENDER_NICKNAME),
						rs.getString(COL_SENDER_TOKEN),
						rs.getString(COL_SEND_DATETIME),
						rs.getString(COL_RECEIVER_TOKEN),
						rs.getString(COL_CONTENTS),
						rs.getInt(COL_STATE)
						);
				msgs.add(m);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) 		try {rs.close();	} catch(Exception e){}
			if(stmt != null) 	try {stmt.close();	} catch(Exception e){}
			if(c != null) 		try {c.close();		} catch(Exception e){}
		}

		return msgs;
	}

	//insert
	/** 
	 * insert a row. except for id. (autoincrement)
	 * @param Message
	 * @return the inserted row count.
	 * @throws SQLException 
	 */
	public int insertNewMessage(Message m) throws SQLException { 
		int result = 0;

		Connection c = null; 
		Statement stmt = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "INSERT INTO " + TABLE_NAME 
					+ " (" + COL_SENDER_ID+ ", " + COL_SENDER_NICKNAME
					+ ", " + COL_SENDER_TOKEN + ", " + COL_SEND_DATETIME
					+ ", " + COL_RECEIVER_TOKEN + ", " + COL_CONTENTS 
					+ ", " + COL_STATE + ")" + " VALUES ('" 
					+ m.getSenderId() + "','" 
					+ m.getSenderNickname() + "','"
					+ m.getSenderToken() + "','" 
					+ m.getSendDatetime() + "','"
					+ m.getReceiverToken() + "','"
					+ m.getContents() + "','" 
					+ m.getState() + "');"; //for boolean
			LogUtil.v("query: " + query);
			result = stmt.executeUpdate(query);
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
	 * update state by id
	 * @param id id
	 * @param state state to update
	 * @return the updated row count.
	 * @throws SQLException 
	 */
	public int updateStateById(long id, int state) throws SQLException { 
		int result = 0;

		Connection c = null; 
		Statement stmt = null;
		try {
			c = getConnection();
			stmt = c.createStatement();
			String query = "UPDATE " + TABLE_NAME
					+ " SET " + COL_STATE + "='" + state + "'"
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

