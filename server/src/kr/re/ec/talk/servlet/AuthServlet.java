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
import kr.re.ec.talk.util.LogUtil;

import com.google.gson.Gson;

/**
 * Servlet for authentication request.
 * @author Kim Taehee 2016-09-17
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
		// 1. get received JSON data from request
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
					&& userDao.isValidUserByToken(authRequest.getToken())) {
				
				LogUtil.v("valid request code and token.");
				authResponse.setSuccess(true);
				authResponse.setMessage("authentication complete.");
			} else {
				throw new Exception("invalid request code or token.");
			}
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
			authResponse.setSuccess(false);
			authResponse.setMessage("authentication failed.");
		}
		
		//set content type
		response.setContentType("application/json"); 
		
		//output
		response.getWriter().print(gson.toJson(authResponse));

	}
}
