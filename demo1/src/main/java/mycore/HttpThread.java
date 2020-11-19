package mycore;

import servlet.CustomerServlet;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import test.savePngtest;

public class HttpThread implements Runnable {
    private Socket socket;

    public HttpThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
        	CustomerServlet myServlet=new CustomerServlet();
        	
        	
//            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Request request=new Request();
            request.setInputStream(socket.getInputStream());
            request.setOutputStream(socket.getOutputStream());
            InputStream input= request.getInputStream();
//            if(!input.markSupported()) {
//            	System.out.println("不支持");
//            }
//            input.mark(2048);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10240);  
//            byte[] attr = new byte[102400];
        	byte[] buffer ;  
        	int totalReadCount = 0 ;  
        	int readCount ;
        	while( (readCount = input.available())>0) {
        		buffer = new byte[readCount];
        		input.read(buffer);
//        System.arraycopy(buffer, 0, attr, totalReadCount, readCount);
        		byteArrayOutputStream.write(buffer, totalReadCount, readCount);
        	}
//        	while ((len = input.read(buffer)) > -1 ) {  
//        		System.out.println("128b");
//        	    byteArrayOutputStream.write(buffer, 0, len);  
//        	}  
        	byteArrayOutputStream.flush(); 
        	InputStream inputStreamA = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        	InputStream inputStreamB = new ByteArrayInputStream(byteArrayOutputStream.toByteArray()); 
//        	将InputStream转换成字符串
        	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamB,"UTF-8"));
//        	BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line=reader.readLine();
            while(line!=null&&!line.equals(""))
            {
                System.out.println(line);
                request.addHeader(line);
                if(line.startsWith("Content-Type")) {
                	String[] pStrings = line.split(";");
                	String ContentType = pStrings[0].split(":")[1].trim();
                	
                	request.setContent_Type(ContentType);
                	if(pStrings.length>=2) {
                		String boundary = pStrings[1].split("=")[1];
                		request.boundary = boundary;
                	}
                	
                }else if(line.startsWith("Content-Length:")) {
                	String Content_Length = line.split(":")[1];
                	Content_Length = Content_Length.trim();
                	int Length = Integer.parseInt(Content_Length);
                	request.setContent_Length(Length);
                }
                line=reader.readLine();
            }
            System.out.println("==========请求 头 完结=======");
            //读取HTTP正文部分
            
            	
            	if(request.getContent_Type()!= null && request.getContent_Type().equals("multipart/form-data")) {
//            		char[] cbuf = new char[Length];
//            		reader.read(cbuf);
//            		OutputStream outputStream=null;
//            		try {
//            		outputStream = new ByteArrayOutputStream();
//            		byte[] buf = new byte[1024];
//            		int len=0;
//            		while ((len = inputStream.read(buf)) !=-1 ) {
//            			outputStream.write(buf, 0, len);
//					}
//            		}catch (Exception e) {
//						// TODO: handle exception
//            			e.printStackTrace();
//					}finally {
//						outputStream.flush();
//						outputStream.close();
//					}
            		System.out.println("multipart");
            		MulitpartData mdata = new MulitpartData();
            		mdata.boundary = request.boundary;
            		mdata.parse(request, inputStreamA);//传文件就不能用reader了
            		request.setmData(mdata);
            		System.out.println("end mulipart");
            	}
            	else if(request.getContent_Length()!=0 && request.getContent_Length()>0){
            		char[] cbuf = new char[request.getContent_Length()];
            		reader.read(cbuf);
            		String string = new String(cbuf);
            		HttpBodyUtl(request, string);
            	System.out.println("=========请求 体 完结========");
            	//解析请求体
//            	if(sbuf==null) {
//            		System.out.println("无数据");
//            	}
//            	else HttpBodyUtl(request, sbuf);
            	}
            	
//            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
//            String line1 = null;
//            StringBuilder sb = new StringBuilder();
//            while((line1 = br.readLine())!=null){
//                sb.append(line1);
//                System.out.println("apend...");
//            }
//            br.close();
//            // 将资料解码
//            String reqBody = sb.toString();
//            System.out.println("reqBody = "+reqBody);
//            while(line!=null)
//            {
//                line=reader.readLine();
//                System.out.println("body:"+line);
//            }
//            System.out.println("==========全部完结======");
            if(!request.getHeader().isEmpty() &&request.getHeader(0).startsWith("GET"))
                myServlet.doGet(request);
            else if(!request.getHeader().isEmpty() && request.getHeader(0).startsWith("POST"))
                myServlet.doPost(request);
            reader.close();
//            inputStreamB.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void HttpBodyUtl(Request request , String sbuf) throws IOException {
    	System.out.println("=========以字符形式解析HTTP Body 开始 =========");
    	HashMap<String, String> mHashMap = new HashMap<String, String>();
    	String[] parmStrings = sbuf.split("&");
    	for(String parm :parmStrings) {
    		String[] kandv = parm.split("=");
    		if(kandv.length>=2) {
    			mHashMap.put(kandv[0], kandv[1]);
    			System.out.println("k = "+kandv[0]+" v= "+kandv[1]);
    		}
    	}
    	request.setAttributes(mHashMap);
    	System.out.println("=========以字符形式解析HTTP Body 结束 =========");
    }
    private void HttpBodyUtlByte(Request request, char[] cbuf) throws IOException {
    	System.out.println("=========解析HTTP Body 开始 =========");
    	BufferedReader reader1 = new BufferedReader(new CharArrayReader(cbuf));
    	HashMap<String, String> mHashMap = new HashMap<String, String>();
    	String line1 = reader1.readLine();
    	String valueString=null;
    	while (line1!=null) {
    		System.out.println(line1);
			if(line1.startsWith("-----")) {
				System.out.println("-----开始");
				if(line1.endsWith("--")) {
					System.out.println("break jiexi body");
					break;
				}
				valueString=null;
			}
			else if(line1.startsWith("Content-Disposition:")) {
				String string1 = line1.split(":")[1];
				String[] string2 = string1.split(";");
				String string3 = string2[0].trim();
				if(string3.equals("form-data")) {//寻找name属性
					for( int i = 1; i<string2.length ; i++) {
						String[] attemps = string2[i].split("=");
						if(attemps.length>1) {
						String keyString = attemps[0].trim();
						if(keyString.equals("name")) {
						valueString = attemps[1].trim().replace("\"", "");
						System.out.println("name= "+valueString);//找到name就跳了
						}
//						if(keyString.equals("filename")) {//字符流不能上传文件
//							valueString = null;
//							String filenameString = attemps[1].trim().replace("\"", "");
//							System.out.println("filename= "+filenameString);	
//							String line2 = reader1.readLine();
////							File file = new File("D:/savePng.png");
////							char[] buf2 = new char[8080];
//							String tempString="";
////							FileOutputStream fosFileOutputStream = new FileOutputStream("D:/log.png");
//							
//							while (line2!=null) {
//								if(line2.startsWith("-----")) {
//									break;
//								}
//								else if(line2.startsWith("Content-Type")) {
//									line2 = reader1.readLine();
//								}//Content-Type 是文件的一部分
//								else {
////									fosFileOutputStream.write(line2.getBytes());
////									tempString=tempString+line2;
//									tempString=tempString+line2+"\r\n";
//									System.out.println("写入一行");
//								}
//								line2 = reader1.readLine();
//								
//							}
////							fosFileOutputStream.close();
//							mHashMap.put(filenameString, tempString);
//							System.out.println("写入完毕");
//							System.out.println(tempString);
//							
//							savePngtest.string2file(tempString, "D:/test.png");
//						}
						}
					}
				}
			}
			else if(line1.equals("")){
				System.out.println("空");
			}
			else if(valueString!=null){
				System.out.println("put : "+valueString+line1);
				mHashMap.put(valueString, line1);
				valueString = null;
			}
			 line1=reader1.readLine();
			 System.out.println("next line1");
		}
    	reader1.close();
    	request.setAttributes(mHashMap);
    	System.out.println("=========解析HTTP Body 完成 =========");
	}

}
