package kr.re.ec.talk.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.re.ec.talk.common.Constants;
import kr.re.ec.talk.dao.UserDao;
import kr.re.ec.talk.dto.AuthRequest;
import kr.re.ec.talk.dto.AuthResponse;
import kr.re.ec.talk.dto.User;
import kr.re.ec.talk.util.LogUtil;

import com.google.gson.Gson;

/**
 * Servlet for authentication request.
 * @author Taehee Kim 2016-09-17
 */
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * code type shoule be belows.
	 */
	private static final String CODE_TYPE_AUTH = "CODE_TYPE_AUTH";
	
	private UserDao userDao;

	public AuthServlet() {
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
		AuthRequest authRequest = gson.fromJson(br, AuthRequest.class);
		LogUtil.v(authRequest.toString());

		//check validation. code and token.
		AuthResponse authResponse = new AuthResponse();
		try {
			if(CODE_TYPE_AUTH.equals(authRequest.getCode()) 
					&& userDao.isValidUserByToken(authRequest.getToken())) { //TODO: update gcm
				
				LogUtil.v("valid request code and token.");
				
				//TODO: if empty deviceId, throw Exception
				User user = userDao.findUserByToken(authRequest.getToken());
				if(user.getDeviceId().equals(authRequest.getDeviceId()) || 
					authRequest.getDeviceId().equals("") ||
					authRequest.getDeviceId().equals("null")) {
					
					LogUtil.v("same device id already exist");
				} else {
					LogUtil.v("update device id");
					userDao.updateDeviceIdById(user.getId(), authRequest.getDeviceId());					
				}
				
				authResponse.setSuccess(true);
				authResponse.setMessage("authentication complete.");
				authResponse.setNickname(user.getNickname());
			} else {
				throw new Exception("invalid request code or token.");
			}
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
			authResponse.setSuccess(false);
			authResponse.setMessage("authentication failed.");
			authResponse.setNickname("");
		}
		
		//set content type
		response.setCharacterEncoding(Constants.CHARSET);
		response.setContentType("application/json"); 
		
		//output
		response.getWriter().print(gson.toJson(authResponse));

	}
}
