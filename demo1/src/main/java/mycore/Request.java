package mycore;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Request {
    private List<String> header=new ArrayList<>();
    private InputStream inputStream;
    private OutputStream outputStream;
    private String BaseUrl;
    private HashMap<String, String> params;//url里面的属性
    private HashMap<String, String> attributes;//HTTP Body里面的属性
    private int Content_Length;
    private String content_Type;
    public  String boundary =null;
    private MulitpartData mData;
    
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
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

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
	public String getParms(String key) {
		return params.get(key);
	}

	public int getContent_Length() {
		return Content_Length;
	}

	public void setContent_Length(int content_Length) {
		Content_Length = content_Length;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getContent_Type() {
		return content_Type;
	}

	public void setContent_Type(String content_Type) {
		this.content_Type = content_Type;
	}

	public MulitpartData getmData() {
		return mData;
	}

	public void setmData(MulitpartData mData) {
		this.mData = mData;
	}
}
