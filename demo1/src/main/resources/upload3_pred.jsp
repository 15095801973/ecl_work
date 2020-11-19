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
						alert("do");
							  var formData = new FormData($("#fd")[0]);  
							 //var formData = new FormData()
							 //formData.append("userName",$("#username"));
							 //formData.append("targetFile",$("#file"));
							 alert(formData);
							 $.ajax({  
								  url: 'http://localhost:8080/upload3/post' ,  
								  type: 'post',  
								  data: formData,  
								  async: true,  
								  cache: false,  
								  contentType: false,  
								  processData: false,  
								  dataType:'json',
								  success: function (data) {  
									  alert("suceess");  
								  }
							 });  
						}  
					</script>
this is upload3
<a>可以通过ajax , mulitpart/form-data传文件</a>
<form id="fd" action="/upload2/post" method="post" enctype="mulitpart/form-data" >
<!-- 没有submit, 所以这个表单不会自己提交 -->
<p>username<input id = "username" name = "username" type="text"></p>

<p>passwd<input name = "passwd" type="password" ></p>

<p><input id="file" name = "filename" type = "file"> </p>
<p><input type="button" value="click me" onclick="doUpload();"></p>
<a href="/Button.png">href</a>
</form>
</body>
</html>