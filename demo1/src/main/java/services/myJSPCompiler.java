package services;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Text;

import com.google.protobuf.Method;

import mycore.Request;
import mycore.Response;

public class myJSPCompiler {
	private static int ContentLength;
    public static String readJSPFile(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        ContentLength = inputStream.available();
        byte[] buf = new byte[ContentLength];
        inputStream.read(buf);
        return new String(buf);
    }

    public static List<String> getTagList(String jspStr){
        int startIndex;
        int endIndex = 0;
        String tag;
        char ch1;
        char ch2;
        List<String> tagList = new ArrayList<>();

        while(true) {
            startIndex = jspStr.indexOf('<',endIndex);
            endIndex = jspStr.indexOf('<',startIndex+1);
            if(endIndex == -1){
                tag = jspStr.substring(startIndex);
                tagList.add(tag);
                break;
            }
            ch1 = jspStr.charAt(startIndex+1);
            ch2 = jspStr.charAt(endIndex + 1);
            if(ch1 == '/'){
                tag = jspStr.substring(startIndex,endIndex);
                tagList.add(tag);
                System.out.println("tag1: "+tag);
            }else if(ch2 == '/'){
                endIndex = jspStr.indexOf('<',endIndex+1);
                tag = jspStr.substring(startIndex,endIndex);
                tagList.add(tag);
                System.out.println("tag2: "+tag);
            }else {
                tag = jspStr.substring(startIndex,endIndex);
                tagList.add(tag);
                System.out.println("tag3: "+tag);
            }
        }

        return tagList;
    }

    public static String readAttr(String tag,String attr){
        int index = tag.indexOf(attr);
        if(index == -1){
            return null;
        }
        int start = tag.indexOf('\"', index);
        int end = tag.indexOf('\"',start+1);
        String contentType = tag.substring(start+1,end);
        return contentType;
    }
    
    public static String getServletString(List<String> tagList,String servletName){
        StringBuilder servletBuf = new StringBuilder("package servlet;\n\n");
        StringBuilder fieldBuf = new StringBuilder();
        StringBuilder preSend = new StringBuilder();
        StringBuilder serviceBody = new StringBuilder();
        preSend.append("response.setContent_Length("+ContentLength+");");
        for (String tag : tagList) {
            if(tag.startsWith("<%--")){//jsp注释
                int end = tag.indexOf('>');
                String text = tag.substring(end+1).trim();
                if(text.length() > 0){
                	text = text.replace("\"", "\\\"");
                    String x = "\t\tout.write(\"" + text + "\");\n";
                    serviceBody.append(x);
                }
            }else if(tag.startsWith("<%@")){//page指令
                String contentType = readAttr(tag,"contentType");
                String importCode = readAttr(tag,"import");
                String pageEncoding = readAttr(tag,"pageEncoding");

                if(contentType != null){
                    String x = "\t\tresponse.setContentType(\"" + contentType + "\");\n";
                    preSend.append(x);
                   }
                if(importCode != null){
                    String x = "import " + importCode + ";\n";
                    servletBuf.append(x);
                }
                if(pageEncoding != null){
                    String x = "\t\tresponse.setCharacterEncoding(\"" + pageEncoding + "\");\n";
                    preSend.append(x);
                           }

                int index = tag.indexOf('>');
                String text = tag.substring(index+1).trim();
                if(text.length() > 0){
                    String x = "\t\tout.write(\"" + text + "\");\n";
                    serviceBody.append(x);
                }
            }else if(tag.startsWith("<%=")){//jsp表达式
                int end = tag.indexOf('>');
                String text = tag.substring(end+1).trim();
                String code = tag.substring(3, end-1);
                String x = "\t\tout.write(" + code + ");\n";
                serviceBody.append(x);
                if(text.length() > 0){
                    String x1 = "\t\tout.write(\"" + text + "\");\n";
                    serviceBody.append(x1);
                }
            }else if (tag.startsWith("<%!")){//全局变量
                int end = tag.indexOf('>');
                String text = tag.substring(end+1).trim();
                String code = "\tprivate "+tag.substring(3,end-1)+"\n";
                fieldBuf.append(code);
                if(text.length() > 0){
                    String x = "\t\tout.write(\"" + text + "\");\n";
                    serviceBody.append(x);
                }
            }else if(tag.startsWith("<%")){//jsp代码
                int end = tag.indexOf('>');
                String text = tag.substring(end+1).trim();
                String code = "\t\t"+tag.substring(2, end-1);
                serviceBody.append(code);
                if(text.length() > 0){
                	text = text.replace("\"", "\\\"");
                    String x = "\t\tout.write(\"" + text + "\");\n";
                    serviceBody.append(x);
                }
            }else {
                String replace = tag.replace("\n", "\\n");
                replace = replace.replace("\"", "\\\"");
                String x = "\t\tout.write(\"" + replace + "\");\n";
                serviceBody.append(x);
            }
        }

        servletBuf.append( 
        		"import java.io.*;\r\n" + 
        		"import mycore.Request;\r\n" + 
        		"import mycore.Response;;\n\n");
        servletBuf.append("\npublic final class ");
        servletBuf.append(servletName);
        servletBuf.append("  {\n");
        servletBuf.append(fieldBuf);
        servletBuf.append("\n\tpublic void service(Request request,Response response) throws IOException {\n"
//                + "\t\tPageContext pageContext = new PageContext(request,response);\n"//生成内置对象
                +"\t\tPrintWriter out = new PrintWriter(new OutputStreamWriter(request.getOutputStream()));\n"
                + "\t\tObject page = this;\n" );
//                "\t\tConfig config = request.getConfig();\n" +
//                "\t\tSession session = request.getSession();\n" +
//                "\t\tApplication application = ServletContext.newInstance();\n\n");
        servletBuf.append(preSend);//写出内容之前可能要做一些处理
        servletBuf.append("\t\tString responseText = response.generateResponseText();\n"
                +"\t\tout.write(responseText);\n\n");//生成响应头和消息
        servletBuf.append(serviceBody);//方法体
        servletBuf.append("\t\tout.close();\n\t}\n}");
        return servletBuf.toString();
    }

    public static void compileJSP(String jspPath,String jspName){
        try {
            String servletName = jspName.substring(0,jspName.indexOf("."));
            servletName = servletName.replace("/", "");
            String targetPath = System.getProperty("user.dir")+"\\"+Constant.PACK_DIR+"servlet\\"+servletName+".java";
            readJSPFile(jspPath);
            String jspStr = readJSPFile(jspPath);
            List<String> tagList = getTagList(jspStr);
            String servletString = getServletString(tagList, servletName);
            System.out.println(targetPath);
            
            OutputStream outputStream = new FileOutputStream(targetPath);
            outputStream.write(servletString.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String jspName = "uploadcomp.jsp";
        String jspPath = "F:\\new_work\\eclipse_work\\demo1\\src\\main\\resources\\upload2.jsp";
        compileJSP(jspPath,jspName);
        
        try {
			Class<?> clasz = ClassLoader.getSystemClassLoader().loadClass("uploadcomp.java");
				
				try {
					Object obj;
					try {
						obj = clasz.newInstance();
						Response response = new Response();
						Request request = new Request();
						java.lang.reflect.Method  method = clasz.getDeclaredMethod("service", Request.class, Response.class);
						method.setAccessible(true);
						method.invoke(obj, request,response);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
    } catch (IllegalAccessException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
