package mycore;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.jni.Buffer;

public class Response {
    private List<String> header=new ArrayList<>();
    private OutputStream outputStream;
    private String BaseUrl;
    private int Content_Length = 0;
    private String ContentType="text/html";
    private String CharacterEncoding="UTF-8";
    public String generateResponseText() {
    	StringBuilder sBuilder = new StringBuilder();
    	sBuilder.append("HTTP/1.1 200 OK\r\n");
    	sBuilder.append("Content-Type: "+ContentType);
//    	+";charset="+CharacterEncoding);
    	sBuilder.append("Content-Length:" + Content_Length);
    	sBuilder.append("\r\n");
    	return sBuilder.toString();
    }
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getHeader(int index) {
        return header.get(index);
    }

    public void addHeader(String line) {
        header.add(line);
    }

	public String getBaseUrl() {
		return BaseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		BaseUrl = baseUrl;
	}


	public int getContent_Length() {
		return Content_Length;
	}

	public void setContent_Length(int content_Length) {
		Content_Length = content_Length;
	}


	public List<String> getHeader() {
		// TODO Auto-generated method stub
		return header;
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	public String getCharacterEncoding() {
		return CharacterEncoding;
	}
	public void setCharacterEncoding(String characterEncoding) {
		CharacterEncoding = characterEncoding;
	}
}
