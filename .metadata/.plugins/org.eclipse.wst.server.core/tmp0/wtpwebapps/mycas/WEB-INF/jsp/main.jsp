<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>main</title>
</head>
<body>
<%
	Cookie cookies[]=request.getCookies();
	for(Cookie cookie:cookies)
	{
		out.println(cookie.getName()+"="+cookie.getValue()+"<br>");
	}
%>
<br>
<%
	String string[]= session.getValueNames();
out.println("id"+session.getId());
	for(String S:string)
	{
		out.println(S+": "+session.getAttribute(S));
	}
%>
LOCAL_ST: ${LOCAL_ST.id }
<br>
USER: ${user }
<br>
LOCAL_USER_ID ${LOCAL_USER_ID }
<p><a href="http://localhost:8080/myapp1/view.do">子系统1</a></p>
<p><a href="http://localhost:8080/myapp2/view.do">子系统2</a></p>
<p><a href="/mycas/logout.do">注销</a></p>

</body>
</html>