<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
					<script type="text/javascript">
						function doUpload() {  
							 // var formData = new FormData($("#uploadForm")[0]);  
							 var formData = new FormData()
							 formData.append("userName",$("#username"));
							 formData.append("targetFile",$("#file"));
							 $.ajax({  
								  url: 'http://localhost:8080/upload2/post' ,  
								  type: 'POST',  
								  data: formData,  
								  async: false,  
								  cache: false,  
								  contentType: false,  
								  processData: false,  
								  dataType:'json',
								  success: function (data) {  
									  alert(data);  
								  }
							 });  
						}  
					</script>
this is jsp/upload2.jsp
<a>可以通过mulitpart/form-data传文件</a>
<form action="/upload2/post" method="post" enctype="multipart/form-data">

<p>username<input name = "username" type="text"></p>

<p>passwd<input name = "passwd" type="password" ></p>

<p><input name = "filename" type = "file"> </p>
<p><input type="submit"></p>
<a href="/Button.png">href
<img src="http://localhost:8080/Button.png" alt="alt">
</a>
</form>
<img src="/Button.png" alt="alt">
</body>
</html>