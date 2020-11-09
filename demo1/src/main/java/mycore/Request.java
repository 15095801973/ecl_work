package mycore;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private List<String> header=new ArrayList<>();
    private InputStream inputStream;
    private OutputStream outputStream;

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
}
