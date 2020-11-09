package mycore;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String args[])
    {
        try {
            ServerSocket serverSocket=new ServerSocket(8080);
            while(true) {
                Socket socket = serverSocket.accept();
                HttpThread t=new HttpThread(socket);
                new Thread(t).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
		}

    }

}
