package kr.re.ec.talk.util;

import java.sql.SQLException;

import kr.re.ec.talk.dao.MessageDao;
import kr.re.ec.talk.dao.UserDao;
import kr.re.ec.talk.dto.Message;
import kr.re.ec.talk.dto.User;

/**
 * DB Initializer for testing.
 * @author Taehee Kim 2016-09-17
 */
public class DBInitializer {
	public static boolean dbInit() {
		//initialize
		//NOTE: do not use
		UserDao userDao = UserDao.getInstance();
		MessageDao messageDao = MessageDao.getInstance();

		try {
			userDao.dropTable();	
			userDao.createTableIfNotExists();

			/**
			 * UUID was generated from https://www.uuidgenerator.net/
			 */
			userDao.insertNewUser(new User(User.ID_NOT_SET, "5de46a86-a867-4d89-8400-639f6016acb2", "서주리[22]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "b4199a46-7467-42de-b0fa-58a4f19cacdd", "김지홍[22]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "873636ce-cedb-4c4a-8695-470a000bd5ef", "최세종[22]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "56e7601e-4f24-447f-b239-caa5cf799467", "주현석[24]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "782750ef-b2f0-4e8f-95ff-419291c64967", "김지혜[24]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "9a17bf93-fde2-406b-ab9e-864b3c38e0ac", "허수정[24]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "8e6f7e60-db5d-498a-bfb1-6c16dfd2e5d5", "조영진[25]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "081eafe7-5e4c-43ab-b041-4e0bb548ce7a", "김재완[25]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "14c4f845-109d-4004-8349-b34c89775fbb", "조연희[26]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "88d4552e-1696-49fa-b144-00e9770b5000", "김성규[26]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "c1220e4c-e872-4ada-b1f0-03261a6e8831", "박주환[26]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "f99b7262-046a-42e3-994b-a96a6f278a8a", "김태희[21]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "6ac62be8-f81d-40e7-b730-3ea627a05b8b", "김희태[00]", ""));

			messageDao.dropTable();	
			messageDao.createTableIfNotExists();
			
			messageDao.insertNewMessage(new Message(
					Message.ID_NOT_SET, 
					1, 
					"서주리[22]", 
					"5de46a86-a867-4d89-8400-639f6016acb2",
					"2016-01-01T00:00:01",
					"f99b7262-046a-42e3-994b-a96a6f278a8a", //12 김태희
					"첫 메시지!! 테스트3",
					Message.STATE_NOT_SENT_TO_CLIENT
					));
			messageDao.insertNewMessage(new Message(
					Message.ID_NOT_SET, 
					1, 
					"서주리[22]", 
					"5de46a86-a867-4d89-8400-639f6016acb2",
					"2016-01-01T00:03:01",
					"6ac62be8-f81d-40e7-b730-3ea627a05b8b", //13 김희태
					"첫 메시지!! 테스트3",
					Message.STATE_SENT_TO_CLIENT
					));
			messageDao.insertNewMessage(new Message(
					Message.ID_NOT_SET, 
					1, 
					"서주리[22]", 
					"5de46a86-a867-4d89-8400-639f6016acb2",
					"2016-01-01T00:02:01",
					"f99b7262-046a-42e3-994b-a96a6f278a8a", //12 김태희
					"첫 메시지!! 테스트3",
					Message.STATE_NOT_SENT_TO_CLIENT
					));
			messageDao.insertNewMessage(new Message(
					Message.ID_NOT_SET, 
					1, 
					"서주리[22]", 
					"5de46a86-a867-4d89-8400-639f6016acb2",
					"2016-01-01T00:00:01",
					"56e7601e-4f24-447f-b239-caa5cf799467", //4 주현석
					"첫 메시지!! 테스트3",
					Message.STATE_SENT_TO_CLIENT
					));
			messageDao.insertNewMessage(new Message(
					Message.ID_NOT_SET, 
					12, 
					"김태희[21]", 
					"f99b7262-046a-42e3-994b-a96a6f278a8a",
					"2016-01-01T00:40:02",
					"b4199a46-7467-42de-b0fa-58a4f19cacdd", //2 김지홍
					"난 김희태가 아니야!!!!!!!!!!!!!!!!!!!!",
					Message.STATE_NOT_SENT_TO_CLIENT
					));
			messageDao.insertNewMessage(new Message(
					Message.ID_NOT_SET, 
					12, 
					"김태희[21]", 
					"f99b7262-046a-42e3-994b-a96a6f278a8a",
					"2016-01-01T00:40:01",
					"5de46a86-a867-4d89-8400-639f6016acb2", //1 서주리
					"난 김희태가 아니야!!!!!!!!!!!!!!!!!!!!",
					Message.STATE_NOT_SENT_TO_CLIENT
					));

			return true;
		} catch (SQLException e) {
			LogUtil.e(e.getMessage());
			return false;
		}
	}
}
