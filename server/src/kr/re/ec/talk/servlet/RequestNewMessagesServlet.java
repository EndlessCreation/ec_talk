package kr.re.ec.talk.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.re.ec.talk.common.Constants;
import kr.re.ec.talk.dao.MessageDao;
import kr.re.ec.talk.dao.UserDao;
import kr.re.ec.talk.dto.AuthRequest;
import kr.re.ec.talk.dto.AuthResponse;
import kr.re.ec.talk.dto.Message;
import kr.re.ec.talk.dto.RequestNewMessagesRequest;
import kr.re.ec.talk.dto.RequestNewMessagesResponse;
import kr.re.ec.talk.util.LogUtil;

import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

/**
 * Servlet for request new messages to client.
 * @author Taehee Kim 2016-09-17
 */
public class RequestNewMessagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * code type shoule be belows.
	 */
	private static final String CODE_TYPE_REQUEST_NEW_MESSAGES = "CODE_TYPE_REQUEST_NEW_MESSAGES";
	
	private MessageDao messageDao;
	private UserDao userDao;
	
	public RequestNewMessagesServlet() {
		messageDao = MessageDao.getInstance();
		userDao = UserDao.getInstance();
		LogUtil.v("dao.getConnection done.");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//get received JSON data from request
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(),Charset.forName(Constants.CHARSET)));

		//convert json request to pojo
		Gson gson = new Gson();
		RequestNewMessagesRequest newRequest = gson.fromJson(br, RequestNewMessagesRequest.class);
		LogUtil.v(newRequest.toString());

		//check validation. code and token.
		RequestNewMessagesResponse messagesResponse = new RequestNewMessagesResponse();
		try {
			if(CODE_TYPE_REQUEST_NEW_MESSAGES.equals(newRequest.getCode()) 
					&& userDao.isValidUserByToken(newRequest.getToken())) {
				
				LogUtil.v("valid request code and token.");

				//get messages
				List<Message> messages = messageDao.findUnsentMessagesByReceiverToken(newRequest.getToken());
				messagesResponse.setMessages(messages);

				//update message state
				for(Message e: messages) {
					messageDao.updateStateById(e.getId(), Message.STATE_SENT_TO_CLIENT);
				}

				messagesResponse.setSuccess(true);
				messagesResponse.setMessage("request new messages complete.");

				LogUtil.v("" + messages.size() + " messages found. response: " + messagesResponse.toString());

			} else {
				throw new Exception("invalid request code or token.");
			}
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
			messagesResponse.setSuccess(false);
			messagesResponse.setMessage("request new messages failed.");
		}
		
		//set content type
		response.setCharacterEncoding(Constants.CHARSET);
		response.setContentType("application/json"); 
		
		//output
		response.getWriter().print(gson.toJson(messagesResponse));
	}
	
}
