package controllers;

import IOC.*;
import mycore.MulitpartData;
import mycore.Request;
import services.Constant;
import services.CustomerServlet;
import services.savePngtest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

import javax.swing.filechooser.FileNameExtensionFilter;
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
		CustomerServlet.doHtml(request, "/upload2.html");
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
			if(spStrings.length>=2) {
				String hzString = spStrings[spStrings.length-1];
				for(String as :Constant.ALLOW_UP_TYPES) {
					if(hzString.equals(as)) {//防止恶意文件上传进来
					        UUID randomUUID = UUID.randomUUID();
					        String filename = spStrings[0]+randomUUID+"."+hzString;
					    String pa = Constant.MEDIA_DIR+filename;
					    System.out.println("savedpath = "+pa);
						try {
							savePngtest.saveByByte(dataEntry.getValue(), pa);
							CustomerServlet.doJson(request, "{\"data\":\""+filename+"\"}");
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
			CustomerServlet.doJson(request, " {\"msg\":\" error \"}");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
