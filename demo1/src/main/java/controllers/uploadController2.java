package controllers;

import IOC.myautowired;
import IOC.mycomponent;
import IOC.mycontroller;
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
		System.out.println("upload2: post ...\n");		
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
}
