package mycore;

import servlet.CustomerServlet;

import java.io.*;
import java.net.Socket;

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
            if(request.getHeader(0).startsWith("GET"))
                myServlet.doGet(request);
            else if(request.getHeader(1).startsWith("POST"))
                myServlet.doPost(request);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
