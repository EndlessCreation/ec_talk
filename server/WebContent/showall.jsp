<%@page import="kr.re.ec.talk.util.DBInitializer"%>
<%@page import="kr.re.ec.talk.dto.User"%>
<%@page import="kr.re.ec.talk.dao.UserDao"%>
<%@page import="kr.re.ec.talk.dto.Message"%>
<%@page import="kr.re.ec.talk.dao.MessageDao"%>
<%@page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	//DBInitializer.dbInit();
	UserDao userDao = UserDao.getInstance(); 
	MessageDao messageDao = MessageDao.getInstance(); %>
<html>
	<head>
		<meta charset="utf-8">
	</head>
	<body>
		<h1>모든 유저</h1>
		<h3>싸그리 불러볼까 ^^?</h3>
		<% ArrayList<User> users = userDao.findAllUsers(); %>
		<% for(User e: users) { %> 
			id: <%= e.getId() %>, 
			token: <%= e.getToken() %>,
			nickname: <%= e.getNickname() %>,
			deviceId: <%= e.getDeviceId() %>
			<br/>
		<% } %>
		아항 끝 !!<br/>
		<br/>
		
		<h1>모든 메시지</h1>
		<h3>싸그리 불러볼까 ^^?</h3>
		<% ArrayList<Message> msgs = messageDao.findTop100Messages(); %>
		<% for(Message e: msgs) { %> 
			id: <%= e.getId() %>, 
			senderId : <%= e.getSenderId () %>,
			senderNickname : <%= e.getSenderNickname() %>,
			senderToken : <%= e.getSenderToken() %>,
			sendDatetime : <%= e.getSendDatetime() %>,
			receiverToken : <%= e.getReceiverToken() %>,
			contents : <%= e.getContents() %>,
			state : <%= e.getState() %>
			<br/>
		<% } %>
		아항 끝 !!<br/>
		<br/>	
	</body>
</html>