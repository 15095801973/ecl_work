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

<title>My JSP 'failed.jsp' starting page</title>
</head>

<body>
	<%
		String ggg = request.getParameter("goto");
		request.setAttribute("ggg", ggg);
	%>
	<h1>${msg}</h1>
	系统将在 <span id="time">6</span> 秒钟后返回【${ggg }】，
	如果未能跳转，<a href="${ggg }" title="点击访问">请点击</a>
	<script type="text/javascript">
		delayURL();
		function delayURL() {
			var delay = document.getElementById("time").innerHTML;
			var t = setTimeout("delayURL()", 1000);
			if (delay > 0) {
				delay--;
				document.getElementById("time").innerHTML = delay;
			} else {
				clearTimeout(t);
				window.location.href = "${ggg }";
			}
		}
	</script>


</body>
</html>
