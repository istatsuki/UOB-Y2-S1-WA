<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval};url=index.jsp">
<title>New EMail Message</title>
</head>
<body>
<form action="ComposingMail" method="get">
Recipient: <input  name="recipient"> <br>
Subject: <input name="subject"><br>
Content:<br>
<textarea rows="10" cols="50" name="content"></textarea><br>
<input type="submit" value="Send EMail">
</form>

<form action="ComposingMail" method="post">
    <input type="submit" name="logOutButton" value="Log Out" />
</form>

Login time(s): <%= session.getAttribute("loginTime") %>
Last login date: <%= session.getAttribute("lastLoginDate") %>
</body>
</html>