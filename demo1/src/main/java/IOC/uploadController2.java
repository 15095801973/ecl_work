package IOC;

import IOC.myautowired;
import IOC.mycomponent;
import mycore.MulitpartData;
import mycore.Request;
import servlet.Constant;
import servlet.CustomerServlet;
import test.savePngtest;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
public class uploadController2 {
	public String name;
	public void setName(String name) {
		this.name=name;
	}

	//value 暂时没有用到
	@myaction(value = "/upload2/get")
	private void get(Request request) {
		System.out.println("upload2: get ...\n");
		
		//			CustomerServlet.doJson(request, "404");
		CustomerServlet.doHtml(request, "/upload2.jsp");
	}
	@myaction(value = "/upload2/post")
	private void post(Request request) {
		
		MulitpartData mData = request.getmData();
//		HashMap<String, byte[]>hashMap
		if(request.getmData()!=null) {
		for(Map.Entry<String, byte[]> dataEntry:mData.dtMap.entrySet()) {
			String pathString = dataEntry.getKey();
			String[]  spStrings =pathString.split("\\.");
			if(spStrings.length==2) {
				for(String as :Constant.ALLOW_UP_TYPES) {
					if(spStrings[1].equals(as)) {//防止恶意文件上传进来
					        UUID randomUUID = UUID.randomUUID();
					    String pa = Constant.MEDIA_DIR+spStrings[0]+randomUUID+"."+spStrings[1];
					    System.out.println("savedpath = "+pa);
						try {
							savePngtest.saveByByte(dataEntry.getValue(), pa);
							CustomerServlet.doJson(request, "upload 2 post succeed");
							return;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
		try {
			CustomerServlet.doJson(request, "upload 2 post failed");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
