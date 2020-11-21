package servlet;

import java.io.IOException;
import myCat.pagecontext.JspWriter;
import myCat.pagecontext.PageContext;
import myCat.request.Request;
import myCat.response.Response;
import myCat.response.ResponseHandler;
import myCat.servlet.Servlet;
import myCat.config.Config;
import myCat.session.Session;
import myCat.servletcontext.Application;
import myCat.servletcontext.ServletContext;


public final class uploadcomp_jsp extends Servlet {

	public void service(Request request,Response response) throws IOException {
		PageContext pageContext = new PageContext(request,response);
		JspWriter out = pageContext.getWriter();
		Object page = this;
		Config config = request.getConfig();
		Session session = request.getSession();
		Application application = ServletContext.newInstance();

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String responseText = ResponseHandler.generateResponseText(response);
		out.write(responseText);

		out.write("<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
");
		out.write("<html>
");
		out.write("<head>
    ");
		out.write("<meta charset="UTF-8">
    ");
		out.write("<title>Title</title>
");
		out.write("</head>
");
		out.write("<body>
");
		out.write("<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
					");
		out.write("<script type="text/javascript">
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
this is upload2
");
		out.write("<a>可以通过mulitpart/form-data传文件</a>
");
		out.write("<form action="/upload2/post" method="post" enctype="multipart/form-data">

");
		out.write("<p>username");
		out.write("<input name = "username" type="text"></p>

");
		out.write("<p>passwd");
		out.write("<input name = "passwd" type="password" ></p>

");
		out.write("<p>");
		out.write("<input name = "filename" type = "file"> </p>
");
		out.write("<p>");
		out.write("<input type="submit"></p>
");
		out.write("<a href="/Button.png">href
");
		out.write("<img src="http://localhost:8080/Button.png" alt="alt">
</a>
");
		out.write("</form>
");
		out.write("<img src="/Button.png" alt="alt">
</body>
");
		out.write("</html>");
		out.close();
	}
}