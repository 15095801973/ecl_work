<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
</head>
<body>

<form action="register.do" method="post">
<p>登录名：<input type="text" name="id"></p>
<p>密码：<input type="password" name="pwd"></p>
<p><input type="submit" value="注册"></p>
</form>
<a href="/mycas/login.do">登录</a>
</body>
</html>