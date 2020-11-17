package mycore;

import servlet.CustomerServlet;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;

public class HttpThread implements Runnable {
    private Socket socket;

    public HttpThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
        	CustomerServlet myServlet=new CustomerServlet();
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Request request=new Request();
            request.setInputStream(socket.getInputStream());
            request.setOutputStream(socket.getOutputStream());
            
            String line=reader.readLine();
            while(line!=null&&!line.equals(""))
            {
                System.out.println(line);
                request.addHeader(line);
                line=reader.readLine();
            }
            System.out.println("==========请求 头 完结=======");
            if(request.getHeader(3).startsWith("Content-Length:"))
            {//读取HTTP正文部分
            	String Content_Length = request.getHeader(3).split(":")[1];
            	Content_Length = Content_Length.trim();
            	int Length = Integer.parseInt(Content_Length);
            	request.setContent_Length(Length);

            	char[] cbuf= new char[Length];
            	int readnum = reader.read(cbuf);
            	System.out.println("readnum = "+readnum);
            	System.out.println(cbuf);
            	System.out.println("=========请求 体 完结========");
            	//解析请求体
            	HttpBodyUtl(cbuf);
            	
            }
            else {
            	System.out.println("=====无请求 体====");
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
            if(request.getHeader(0).startsWith("GET"))
                myServlet.doGet(request);
            else if(request.getHeader(1).startsWith("POST"))
                myServlet.doPost(request);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void HttpBodyUtl(char[] cbuf) throws IOException {
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
			}
			 line1=reader1.readLine();
			 System.out.println("next line1");
		}
    	reader1.close();
    	System.out.println("=========解析HTTP Body 完成 =========");
	}

}
