package servlet;

import java.io.*;
import mycore.Request;
import mycore.Response;;


public final class uploadcomp  {

	public void service(Request request,Response response) throws IOException {
		PrintWriter out = new PrintWriter(new OutputStreamWriter(request.getOutputStream()));
		Object page = this;
response.setContent_Length(1539);		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String responseText = response.generateResponseText();
		out.write(responseText);

		out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
		out.write("<html>\n");
		out.write("<head>\n    ");
		out.write("<meta charset=\"UTF-8\">\n    ");
		out.write("<title>Title</title>\n");
		out.write("</head>\n");
		out.write("<body>\n");
		out.write("<script src=\"http://libs.baidu.com/jquery/2.1.4/jquery.min.js\"></script>\n					");
		out.write("<script type=\"text/javascript\">\n						function doUpload() {  \n							 // var formData = new FormData($(\"#uploadForm\")[0]);  \n							 var formData = new FormData()\n							 formData.append(\"userName\",$(\"#username\"));\n							 formData.append(\"targetFile\",$(\"#file\"));\n							 $.ajax({  \n								  url: 'http://localhost:8080/upload2/post' ,  \n								  type: 'POST',  \n								  data: formData,  \n								  async: false,  \n								  cache: false,  \n								  contentType: false,  \n								  processData: false,  \n								  dataType:'json',\n								  success: function (data) {  \n									  alert(data);  \n								  }\n							 });  \n						}  \n					</script>\nthis is upload2\n");
		out.write("<a>可以通过mulitpart/form-data传文件</a>\n");
		out.write("<form action=\"/upload2/post\" method=\"post\" enctype=\"multipart/form-data\">\n\n");
		out.write("<p>username");
		out.write("<input name = \"username\" type=\"text\"></p>\n\n");
		out.write("<p>passwd");
		out.write("<input name = \"passwd\" type=\"password\" ></p>\n\n");
		out.write("<p>");
		out.write("<input name = \"filename\" type = \"file\"> </p>\n");
		out.write("<p>");
		out.write("<input type=\"submit\"></p>\n");
		out.write("<a href=\"/Button.png\">href\n");
		out.write("<img src=\"http://localhost:8080/Button.png\" alt=\"alt\">\n</a>\n");
		out.write("</form>\n");
		out.write("<img src=\"/Button.png\" alt=\"alt\">\n</body>\n");
		out.write("</html>");
		out.close();
	}
}