<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>login</title>
</head>
<body >
	<% 
	   String ggg = request.getParameter("goto");
	   request.setAttribute("ggg", ggg);
	%>
	<c:if test="${ggg == null }">请从子系统处访问！</c:if>
	<c:if test="${ggg != null }">
	    <h1>终端认证</h1>
		<form action="<%=basePath %>SSOAuth" method="post">
			       <input type="hidden" name="goto" value='<%=request.getParameter("goto")%>' />
			用户名: <input type="text" name="username" >
			密码 :  <input type="password" name="password" > 
				   <input type="submit" value="登录" />
		</form>
	</c:if>
	
</body>
</html>
