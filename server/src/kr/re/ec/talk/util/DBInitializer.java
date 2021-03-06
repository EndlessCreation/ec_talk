package kr.re.ec.talk.util;

import java.sql.SQLException;

import kr.re.ec.talk.common.Constants;
import kr.re.ec.talk.dao.MessageDao;
import kr.re.ec.talk.dao.UserDao;
import kr.re.ec.talk.dto.Message;
import kr.re.ec.talk.dto.User;

/**
 * DB Initializer for testing.
 * @author Taehee Kim 2016-09-17
 */
public class DBInitializer {
	/**
	 * Database initialize and insert dummy data. Only available on debug mode.
	 * @return true is init success.
	 */
	public static boolean dbInit() {
		
		if(!Constants.DEBUG) return false;
		
		//initialize
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
			userDao.insertNewUser(new User(User.ID_NOT_SET, "c1220e4c-e872-4ada-b1f0-03261a6e8831", "강주호[23]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "f99b7262-046a-42e3-994b-a96a6f278a8a", "김태희[21]", ""));
			userDao.insertNewUser(new User(User.ID_NOT_SET, "6ac62be8-f81d-40e7-b730-3ea627a05b8b", "이호상[22]", ""));

			messageDao.dropTable();	
			messageDao.createTableIfNotExists();
			
			for(User user: userDao.findAllUsers()) {
				//insert initial value
				// id: 13, senderId : 12, senderNickname : 김태희[21], senderToken : f99b7262-046a-42e3-994b-a96a6f278a8a,
				// sendDatetime : 1988-07-21 05:02:23.0, receiverToken : 6ac62be8-f81d-40e7-b730-3ea627a05b8b, 
				// contents : 이씨톡에 오신 것을 환영하여요!, state : 1
				messageDao.insertNewMessage(new Message(
						Message.ID_NOT_SET,
						12,
						"김태희[21]",
						"f99b7262-046a-42e3-994b-a96a6f278a8a",
						"1988-07-21 05:55:55." + (100 + user.getId()), //make order
						user.getToken(),
						"이씨톡에 온 것을 환영하여요!",
						Message.STATE_NOT_SENT_TO_CLIENT
						));
			}

			return true;
		} catch (SQLException e) {
			LogUtil.e(e.getMessage());
			return false;
		}
	}
}
