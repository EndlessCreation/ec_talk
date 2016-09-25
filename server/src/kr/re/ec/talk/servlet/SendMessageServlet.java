package kr.re.ec.talk.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.re.ec.talk.common.Constants;
import kr.re.ec.talk.dao.MessageDao;
import kr.re.ec.talk.dao.UserDao;
import kr.re.ec.talk.dto.Message;
import kr.re.ec.talk.dto.SendMessageRequest;
import kr.re.ec.talk.dto.SendMessageResponse;
import kr.re.ec.talk.dto.User;
import kr.re.ec.talk.util.LogUtil;

import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

/**
 * Servlet for send message.
 * @author Taehee Kim 2016-09-17
 */
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * code type shoule be belows.
	 */
	private static final String CODE_TYPE_SEND_MESSAGE = "CODE_TYPE_SEND_MESSAGE";
	
	private UserDao userDao;
	private MessageDao messageDao;

	//for gcms
	private static final String SERVER_AUTH_KEY = "AIzaSyCdxkOzkMFe_2bZixWgO-spEedo-x8F_lQ";
	
	public SendMessageServlet() {
		userDao = UserDao.getInstance();
		messageDao = MessageDao.getInstance();
		LogUtil.v("dao.getConnection done.");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//get received JSON data from request
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(),Charset.forName(Constants.CHARSET)));

		//convert json request to pojo
		Gson gson = new Gson();
		SendMessageRequest sendMessageRequest = gson.fromJson(br, SendMessageRequest.class);
		LogUtil.v(sendMessageRequest.toString());

		//check auth. code and token.
		SendMessageResponse sendMessageResponse = new SendMessageResponse();
		try {
			if(CODE_TYPE_SEND_MESSAGE.equals(sendMessageRequest.getCode()) 
					&& userDao.isValidUserByToken(sendMessageRequest.getToken())) {
				
				LogUtil.v("valid request code and token.");
				
				Message toSendMessage = sendMessageRequest.getMessage();
				
				//make copy of messages per all users
				ArrayList<User> allUsers = userDao.findAllUsers();
				User userWhoSent = userDao.findUserByToken(toSendMessage.getSenderToken());
				
				for(User user: allUsers) {
					if(!(userWhoSent.getToken().equals(user.getToken()))) {
						LogUtil.v(userWhoSent.toString());
						//insert initial value except for SenderToken, sendDatetime and Contents 
						toSendMessage.setId(Message.ID_NOT_SET);
						toSendMessage.setSenderId(userWhoSent.getId());
						toSendMessage.setSenderNickname(userWhoSent.getNickname());
						toSendMessage.setReceiverToken(user.getToken());
						toSendMessage.setState(Message.STATE_NOT_SENT_TO_CLIENT);
						
						if(messageDao.insertNewMessage(toSendMessage) != 1) {
							throw new Exception("cannot insert copy of messages to db");
						} //TODO: this should be 1. make exception
					}
				}

				//send GCM
				Sender sender = new Sender(SERVER_AUTH_KEY);

				// use this to send message with payload data
				com.google.android.gcm.server.Message message = new com.google.android.gcm.server.Message.Builder()
				.collapseKey("message")
				.timeToLive(3)
				.delayWhileIdle(true)
				.addData("message", toSendMessage.getContents()) 
				.addData("nickname", toSendMessage.getSenderNickname())
				.addData("datetime", toSendMessage.getSendDatetime())
				.build(); 

				//Use this code to send notification message to multiple devices
				//add your devices RegisterationID, one for each device               
				List<String> devicesList = userDao.findAllDeviceIdsExceptForSender(toSendMessage.getSenderToken());
				for(String deviceId: devicesList) {
					LogUtil.v("target deviceId: " + deviceId);
				}
				LogUtil.v("target devicesList.size(): " + devicesList.size());
				
				//Use this code for multicast messages   
				MulticastResult multicastResult = sender.send(message, devicesList, 0);
				LogUtil.v("Message Result: "+multicastResult.toString());//Print multicast message result on console
				
				//set response
				sendMessageResponse.setSuccess(true);
				sendMessageResponse.setMessage("send message complete");
			} else {
				throw new Exception("invalid request code or token or format.");
			}
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
			sendMessageResponse.setSuccess(false);
			sendMessageResponse.setMessage("send message failed.");
		} 
		
		//set content type
		response.setCharacterEncoding(Constants.CHARSET);
		response.setContentType("application/json"); 
		
		//output
		response.getWriter().print(gson.toJson(sendMessageResponse));

	}
}
