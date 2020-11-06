<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>这是App1</p>
id:${user.id }
name:${user.name }
age:${user.age }
email:${user.email }
<br>
<form action="update.do" method="post">
<p>id：<input type="text" name="id" value="${user.id }" readonly></p>
<p>name：<input type="text" name="name" value="${user.name }"></p>
<p>age：<input type="text" name="age" value="${user.age}" ></p>
<p>email：<input type="text" name="email" value="${user.email}" ></p>
<p><input type="submit" value="提交"></p>
</form>
<p><a href="http://localhost:8080/mycas/logout.do">注销</a></p>
</body>
</html>