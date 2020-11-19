package servlet;

import servlet.CustomerServlet.LoadType;

public final class Constant{
	public static String XML_MAPS="maps";
	public static String XML_MAP_VALUE="value";
	public static String XML_MAP_CLASS="class";
	public static String XML_MAP_METHOD="method";
	public static String MULTIPART_FORM_DATA="multipart/form-data";
	public static String LINEEND = "\r\n";
	public static String MEDIA_DIR = "\\src\\main\\resources\\media\\";
	public static String PACK_DIR = "\\src\\main\\java\\";
	public static String PACK_NAME = "";//意为扫描所有包
	public static LoadType LOAD_T = LoadType.ScanXML;
	//注入的两种方式, 扫描包或者解析xml文件.
	public static String[] ALLOW_UP_TYPES = {"jpg", "png", "rar", "doc", "docx", "pdf",
			"zip", "txt"
	};
	
	
}