<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
</head>
<body>
this is upload
<a>不能传文件</a>
<form action="/upload/post" method="post">

<p>username<input name = "username" type="text"></p>

<p>passwd<input name = "passwd" type="password" ></p>

<p><input name = "filename" type = "file"> </p>
<p><input type="submit"></p>
<a href="/Button.png">href</a>
</form>
</body>
</html>