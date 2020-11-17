package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.InitBinder;

import IOC.myaction;
import IOC.mycomponent;
import IOC.mycontroller;
import mycore.HttpThread;
import mycore.Request;
import servlet.Constant;

//public class CustomerServlet extends HttpServlet {
public class CustomerServlet{
	
	
	private static HashMap<String, Method> methodMap= new HashMap<String, Method>();
	private static HashMap<String, String> method2clasz= new HashMap<String, String>();
	private static HashMap<String, Object> objMap= new HashMap<String, Object>();
	private static final long serialVersionUID = 1L;

	public CustomerServlet() {
		System.out.println("初始化。。。");
		try {//解析xml
			load_file("src/main/resources/beans.xml");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			load_file("src/main/resources/beans.xml");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	public void doGet(Request request)  {
		System.out.println("doGet");
			doPost(request);
	}

	public void doPost(Request request) {
		// 获取请求的URI地址信息
        String url=request.getHeader(0).split(" ")[1];
        url=urlutl(request, url);
		// 截取其中的方法名
		String methodName = url;//.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		System.out.println("doPost::methodName="+methodName);
		Method method = null;
			// 使用反射机制获取在本类中声明了的方法
			method = methodMap.get(methodName);
			String claszNameString=method2clasz.get(methodName);
			Object obj=objMap.get(claszNameString);
			System.out.println("mothod="+method);
			System.out.println("clas="+claszNameString);
			System.out.println("obj="+obj);
//			for( Map.Entry<String, String> entry:method2clasz.entrySet()){
//				System.out.println("method2class");
//				System.out.println(entry.getKey()+"+"+entry.getValue());
//			}
//			for( Map.Entry<String, Object> entry:objMap.entrySet()){
//				System.out.println("objMap");
//				System.out.println(entry.getKey()+"+"+entry.getValue());
//			}
//			for( Map.Entry<String, Method> entry:methodMap.entrySet()){
//				System.out.println("methodmap");
//				System.out.println(entry.getKey()+"+"+entry.getValue());
//			}
			// 执行方法
			try {
				if(method!=null) {
				//设置访问权限，关键
				method.setAccessible(true);
				method.invoke(obj, request);
				}
				else {//返回404页面
					doHtml(request, "/noFound.html");
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("can not found /noFound.html");
				e.printStackTrace();
			}
	
	}
	//map hander
	private static void load_file(String file) throws ClassNotFoundException {
		   //1.创建Reader对象
     SAXReader reader = new SAXReader();
     //2.加载xml
     Document document;
		try {
			document = reader.read(new File(file));
		
     //3.获取根节点
     Element rootElement = document.getRootElement();
     Iterator iterator = rootElement.elementIterator();
     while (iterator.hasNext()){
         Element stu = (Element) iterator.next();
         System.out.println("::"+stu.getName());
         if( !stu.getName().equals(Constant.XML_MAPS)) {
        	 continue;
         }
         System.out.println("get servlet maps from .xml");
         Iterator iter0=stu.elementIterator();
         
         while(iter0.hasNext()) {
        	 
         Element stu0 = (Element) iter0.next();
         List<Attribute> attributes = stu0.attributes();
         
         System.out.println("======开始获取各个属性======");
         String value=null,clasz=null,method=null;
         for (Attribute attribute : attributes) {
         	System.out.println("name:"+attribute.getName()+"; vlaue:"+attribute.getValue());
         	if(attribute.getName().equals(Constant.XML_MAP_VALUE))
         	{
                 System.out.println(Constant.XML_MAP_VALUE+attribute.getValue());
                 value=attribute.getValue();
         	}
         	else if (attribute.getName().equals(Constant.XML_MAP_CLASS)) {
         		clasz=attribute.getValue();
			}
         	else if (attribute.getName().equals(Constant.XML_MAP_METHOD)) {
				method=attribute.getValue();
			}
         }
         
         try {
        	 System.out.println("ready to put:"+value+" "+clasz+" "+method);
			put_method(value,clasz,method);
			System.out.println("put into methodMap");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         }
		}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//添加方法进去，顺便将方法的对象添加进去，反射要用
	private static void put_method(String valueString,String claszString,String methodString) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException {
		Class<?> clazz=ClassLoader.getSystemClassLoader().loadClass(claszString);
		Annotation[] annoList=clazz.getAnnotations();
		System.out.println(annoList.length);
		for(int i=0;i<annoList.length;i++) {
			System.out.println(annoList[i].annotationType());
			//只对有mycontroller注解的类进行映射
			if(annoList[i].annotationType()==mycontroller.class) {
				//request and response 是方法的参数
				Method myMethod=clazz.getDeclaredMethod(methodString, Request.class);
				if(myMethod.getAnnotation(myaction.class)!=null) {
					//如果这个方法有注解才加入
					try {//顺便将方法的对象添加进去，反射要用
						objMap.put(claszString, clazz.newInstance());
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					method2clasz.put(valueString, claszString);
					methodMap.put(valueString, myMethod);
				System.out.println("add class:"+claszString+"method:"+methodString);
				}
				else {
					System.out.println("class:"+claszString+" have annotation but\n method:"+methodString+"haven‘t annotation");
				}
			}
	}
	}
	
	private void queryEmp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("执行查询员工的方法...");
		response.setContentType("text/html;charset=utf8");
		PrintWriter pw = response.getWriter();
		pw.println("<h1>查询员工的方法</h1>");
		pw.flush();
		pw.close();
	}
	public static void doJson(Request request,String content) throws UnsupportedEncodingException {
    	//name即是html地址
        PrintWriter writer=new PrintWriter(new OutputStreamWriter(request.getOutputStream()));
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html;charset=UTF-8");
        writer.println("Content-Length:"+content.getBytes("UTF-8").length);
        writer.println();
        writer.print(content);
        writer.flush();
        writer.close();
	}
	public static void doHtml(Request request,String name) throws IOException {
    	//name即是html地址
        String content=loadHtml(name);
        PrintWriter writer=new PrintWriter(new OutputStreamWriter(request.getOutputStream()));
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html;charset=UTF-8");
        writer.println("Content-Length:"+content.getBytes("UTF-8").length);
        writer.println();
        writer.print(content);
        writer.flush();
        writer.close();

    }

	public static String loadHtml(String name) throws IOException {
        BufferedReader reader=new BufferedReader(new InputStreamReader(HttpThread.class.getResourceAsStream(name)));
        StringBuffer buffer=new StringBuffer();
        String line=reader.readLine();
        while(line!=null)
        {
            buffer.append(line);
            buffer.append("\n");
            line=reader.readLine();
        }
        return buffer.toString();

    }

	public static String urlutl(Request request,String url) {
		url=url.trim();
		if(url.equals("")) {
			System.out.println("urlpats为空");
			return null;
		}
		String[] urlParts = url.split("\\?");
		request.setBaseUrl(urlParts[0]);
		HashMap<String, String> parmsMap=new HashMap<String, String>();
		if(urlParts.length == 1) {
		//有参数
		String[] parms = urlParts[1].split("&");
		for(String parm : parms) {
			String[] keyValue = parm.split("=");
			parmsMap.put(keyValue[0], keyValue[1]);
		}
		}
		//没有参数也设置个空的,
		request.setParams(parmsMap);
		return urlParts[0];
	}

}
