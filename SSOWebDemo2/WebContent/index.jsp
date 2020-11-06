<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>index</title>
  </head>
  
  <body>
  			<h1>欢迎用户：${SSOUser }</h1>
			<h3>单点登录测试子系统【2】
				<c:if test="${SSOUser !=null }">
					<a href="index.jsp?action=logout">退出登录</a>
				</c:if>
			</h3>
  </body>
</html>
