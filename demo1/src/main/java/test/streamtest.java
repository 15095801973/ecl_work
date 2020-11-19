package test;

import servlet.CustomerServlet;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import test.savePngtest;
public class streamtest {
public static void main(String[] args) throws IOException {
	File file = new File("D:/testst.txt");
//	InputStream in = System.in;
	InputStream in = new FileInputStream(file);
	ByteArrayOutputStream byt = new ByteArrayOutputStream();
	byte[] bbuf = new byte[4];
	int len=0;
	while( (len = in.read(bbuf)) !=-1) {
		byt.write(bbuf,0 ,len);
	}
	byt.flush();
	InputStream inputStreamB = new ByteArrayInputStream(byt.toByteArray()); 
	InputStream inputStreamA = new ByteArrayInputStream(byt.toByteArray()); 
	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamA));
	String line = reader.readLine();
	System.out.println("line:"+line);
	while(line !=null ) {
		line = reader.readLine();
		System.out.println("line:"+line);
	}
	line = reader.readLine();
	line = reader.readLine();
	int l2=0;
	while( (l2 = inputStreamB.read()) !=-1 ) {
		System.out.println("char:="+(char)l2);
	}

	in.close();
	
}

}
