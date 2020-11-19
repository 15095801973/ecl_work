package IOC;

import IOC.myautowired;
import IOC.mycomponent;
import mycore.Request;
import servlet.CustomerServlet;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import IOC.myaction;
import testAnna.Interceptor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
@mycontroller
public class uploadController {
	public String name;
	public void setName(String name) {
		this.name=name;
	}

	//value 暂时没有用到
	@myaction(value = "/upload/get")
	private void get(Request request) {
		System.out.println("upload: get ...\n");
		
		CustomerServlet.doHtml(request, "/upload.jsp");
	}
	@myaction(value = "/upload/post")
	private void post(Request request) throws IOException {
		CustomerServlet.doJson(request, "post succeed");
		
	}
	private void action(Request request) {
		
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		DiskFileItemFactory sf= new DiskFileItemFactory();//实例化磁盘被文件列表工厂
////		String path = request.getRealPath("/upload");//得到上传文件的存放目录
//		String path = "D:\button.png";
//		sf.setRepository(new File(path));//设置文件存放目录
//		sf.setSizeThreshold(1024*1024);//设置文件上传小于1M放在内存中
//		String rename = "";//文件新生成的文件名
//		String fileName = "";//文件原名称
//		String name = "";//普通field字段
//		//从工厂得到servletupload文件上传类
//		ServletFileUpload sfu = new ServletFileUpload(sf);
//		
//		try {
//			HashMap<String, String> paemsMap=request.getParams();//得到request中所有的元素
//			for (FileItem fileItem : lst) {
//				if(fileItem.isFormField()){
//					if("name".equals(fileItem.getFieldName())){
//						name = fileItem.getString("UTF-8");
//					}
//				}else{
//					//获得文件名称
//					fileName = fileItem.getName();
//					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
//					String houzhui = fileName.substring(fileName.lastIndexOf("."));
//					rename = UUID.randomUUID()+houzhui;
//					fileItem.write(new File(path, rename));
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		response.sendRedirect("message.jsp");
//		out.flush();
//		out.close();
//
}
}
