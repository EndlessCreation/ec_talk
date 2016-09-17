//package kr.re.ec.abeeksurvey.ajax;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import kr.re.ec.abeeksurvey.dao.SurveyDao;
//import kr.re.ec.abeeksurvey.vo.AjaxRequestVo;
//import kr.re.ec.abeeksurvey.vo.SurveyVo;
//import kr.re.ec.talk.common.Constants;
//import kr.re.ec.talk.dao.MessageDao;
//import kr.re.ec.talk.util.LogUtil;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//
///**
// * Ajax Handler for index.html</br>
// * This provides survey list.
// * @author Kim Taehee
// *
// */ //TODO: to correct???
//public class MessageReceiverServlet extends HttpServlet {
//	private MessageDao messageDao;
//
//	public MessageReceiverServlet() {
//		messageDao = MessageDao.getInstance();
//		LogUtil.v("dao.getConnection done.");
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// 1. get received JSON data from request
//		BufferedReader br = new BufferedReader(new InputStreamReader(
//				request.getInputStream(),Charset.forName(Constants.CHARSET)));
//		String json = "";
//		if(br != null){
//			json = br.readLine();
//			LogUtil.d("linedata: " + json);
//		}
//		
//		Gson gson = new Gson();
//		Auth
//
//		// 2. initiate jackson mapper
//		ObjectMapper mapper = new ObjectMapper();
//		//mapper.configure(DeserializationCon .Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//
//		// 3. Convert received JSON to Article
//		AjaxRequestVo ajax = mapper.readValue(json, AjaxRequestVo.class);
//		
//		//invalid access
//		if(!ajax.getReqType().equals(AjaxRequestVo.TYPE_REQ_SURVEY)) { 
//			String errStr = "invalid access. request type mismatched.";
//			LogUtil.e(errStr);
//			throw new IOException(errStr);
//		}
//
//		// 4. Set response type to JSON
//		response.setContentType("application/json");  
//
//		// get started survey
//		LogUtil.i("surveyId is: " + ajax.getSurveyId());
//		try {
//			if(ajax.getSurveyId()==null) {
//				surveyArl = surveyDao.findSurveyByState(SurveyVo.STATE_STARTED);
//			} else {
//				surveyArl = new ArrayList<SurveyVo>(); 
//				surveyArl.add(surveyDao.findSurveyById(ajax.getSurveyId()));
//				
//			}
//			//log test
//			if (surveyArl != null) {
//				if (surveyArl.size() != 0) {
//					LogUtil.d("surveyArl(0).EndDate: " + surveyArl.get(0).getEndDate()); 
//				}
//			}
//		} catch (SQLException e) {
//			LogUtil.e("cannot select");
//			e.printStackTrace();
//		}
//		LogUtil.d("surveyArl size: " + surveyArl.size());
//		//surveyArl.add(survey);
//
//		// 6. Send List<Article> as JSON to client
//		mapper.writeValue(response.getOutputStream(), surveyArl);    
//	}
//}
