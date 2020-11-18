package test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class savePngtest {
	public static String codeType = "iso-8859-1";
	
public static void printHexString( byte[] b) {
for (int i = 0; i < b.length; i++) {
String hex = Integer.toHexString(b[i] & 0xFF);
if (hex.length() == 1) {
hex = '0' + hex;
}
System.out.print(hex.toUpperCase() );
}

}

public static void string2file(String datas,String path) throws IOException {
	File file = new File(path);
	byte[] byt = datas.getBytes("ascii");
//	System.out.println(Arrays.toString(byt));
	InputStream inputStream = new ByteArrayInputStream(byt);
//	printHexString(byt);
	OutputStream outputStream =null;
	try {
		System.out.println("try!!");
		System.out.println(datas);
		outputStream = new FileOutputStream(file);
		int len=0;
		byte[] buf = new byte[1024];
		while((len = inputStream.read(buf)) !=-1 ) {
			outputStream.write(buf, 0, len);
			System.out.println("写一次缓存");
		}
		
	} catch (FileNotFoundException e) {
		System.out.println("ERROR???");
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		outputStream.flush();
		outputStream.close();
		System.out.println(path+" 已经完成了!");
	}
}

public static void main(String[] args) throws IOException {
//	test_p2s();
	File file = new File("D:/Button.png");
	if(!file.exists()) {
		return;
	}
	File file2 = new File("D:/Button3.png");
	ByteArrayOutputStream  outputStream = new ByteArrayOutputStream();
	InputStream  inputStream =new FileInputStream(file);
	try {
		byte[] buff = new byte[1024];
		int len =0;
		while((len = inputStream.read(buff))!=-1){
			outputStream.write(buff, 0, len);
		}
		
	}
	catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	finally {
		outputStream.flush();
		outputStream.close();
	}
	
	byte[] byt1 = outputStream.toByteArray();
	BASE64Encoder encoder = new BASE64Encoder();
	String se = encoder.encode(byt1);
	String sb= new String(byt1,"iso-8859-1");
//	String sb= new String(byt1,"gbk");
//	for( int i=0, len = byt1.length ; i<len; ++i) {
//		if(byt1[i]<=0) {
//			byt1[i] +=256;
//		}
//	}
	System.out.println(se);
	System.out.println(sb);
	byte[] byt2 = sb.getBytes("iso-8859-1");
//	Writer fwt = new FileWriter(file2);
//	OutputStream otStream = new FileOutputStream(file2);
//	Writer out=new BufferedWriter(new OutputStreamWriter(otStream));
//    out.write(sb);
//    out.close();
//	byt = outputStream.toString().getBytes();

	OutputStream outputStream1 = new FileOutputStream(file2);
	InputStream  inputStream1 =new ByteArrayInputStream(byt2);
	try {
		byte[] buff = new byte[1024];
		int len =0;
		while((len = inputStream1.read(buff))!=-1){
			outputStream1.write(buff, 0, len);
		}
		
	}
	catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	finally {
		outputStream1.flush();
		outputStream1.close();
	}
	
	
}
public static void test_p2s() throws IOException {
	File file = new File("D:/Button.png");
	if(!file.exists()) {
		return;
	}
	OutputStream outputStream = null;
	InputStream  inputStream =new FileInputStream(file);

	try {
		outputStream = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int len =0;
		while((len = inputStream.read(buff))!=-1){
			outputStream.write(buff, 0, len);
		}
		
	}
	catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	finally {
		outputStream.flush();
		outputStream.close();
	}
	String outString = outputStream.toString();
	System.out.println(outString);
	System.out.println("===========");
//	byte[] byt = outputStream.toString().getBytes();
//	System.out.println(byt);
	BASE64Encoder encoder = new BASE64Encoder();
	String hasencode = encoder.encode(outString.getBytes());
	System.out.println(hasencode);
	test_s2p(hasencode);
	
}
public static void test_s2p(String st) throws IOException {
	String tempString ="iVBORw0KGgoAAAANSUhEUgAAAFAAAABQCAYAAACOEfKtAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAOPSURBVHhe7Zw7aBVBFIaTiGIgRYqopAmIgUC6xCZliiipArHyUaiVolZjaaGFpZ0PLLVQsYitqIWNjpV2gqAoNuKjsFAE3/5/9uRxbw57s96Zc3du5oMPLnNn5/y7cAPZObs9mUwmk8lkMplMrXF+DN4Vx2Q00xLnB+EF+B3+FfmZY4MyK7MG5/vgMfgJLl24ZvndcdgnR2UWcX4aPoPaRdPk3Gk5egPj/AhckIvyP96BI7LaBsL5AXgefoPahaki1+BaA7J6F+N8LzwI30HtYrQj1zwEe6Val+H8FHwCtZMPKWtMSdUuwPlheA3+gdoJx5C1rsNhSZEgzvfDM/AL1E7SQtZmhn5JlQjOz8PXUDupTvgGzku6GuP8BHwooesos01I2hrh/BC8DH9BLXid/A2ZdUjSdxDnN8PT8DPUwtZZZmb2LXI2xjg/C59DLVxK8hxm5awM0cOkqzlaiJQ1RwuRsuZoIVLWHC1EypqjhUhZc7QQKWuOFiJlzdFCpKw5WoiUNUcLkbLmaCFS1hwtRMqao4VIWXO0EClrjhYiZc3RQqSsOWtDPIaPmsbS0ZyV4rwlPiejHJ+TscaAddcc59/Co5B9fdzOvAfvy2eOHYGcoweum+Y4vxXugjfh6rYNfubYKOQc7nyVNU3WQ1Oc3wYvwtVtuM3yO87ZDtm2yxa0r1Cb23lNKPr6zsIq/S6cy2N4LBuNrsCfUJvbOaPCzWfnT8L3ywWr+wFyDa7Fn/dtaNmxVW4UiobI/fDlcqH25Vpck2vvhg+gNs/W4Dg/A582FAkr156RWtr3tgZHKxJDy1plBkcrEkPLWmUGRysSQ8taZQZHKxJDy1plBkcrEkPLWmUGRysSQ8taui9ghP5AvVh4LWs1GrlDVS8aXstaheyRvgoj90jrxcNrWcu0S18PEF6bWrwvuW+xlhl6kPDGrdXBJ5X0QOGNU4t3eW7ADj4rpwcLb/haNXlaUw8X3nC1+LzwYViT54X1kOFtv1ZNn1h3fg+s8iKIqnLtvVJL+3498p0JOxfXqCXFXeMDMOQd6VeQa6781Lr+rR3FPsYpyH0N7YTW40fINfR/nYq95BOw1XtjOGeTHJUYxc7aOVh1V47HrO9vlP7moh8y1iVvLir2ei/BVvvCnLNDjqqG8+Nw6d1Z4zLaZRTbk7dgc2cCx0ZlVqYlzk9Cbk/SSRnNZDKZTCaTyWQyGdLT8w+tP5TGBQmnmQAAAABJRU5ErkJggg==";
	File fileFile = new File("D:/temp2.png");
	System.out.println("open  D:/temp.png");
//	BASE64Decoder decoder = new BASE64Decoder();
//	byte[] byt = decoder.decodeBuffer(st);
	byte[] byt = st.getBytes("utf-8");
	OutputStream outputStream =null;
	InputStream  inputStream = new ByteArrayInputStream(byt);
	try {
		outputStream = new FileOutputStream(fileFile);
		byte[] buff = new byte[1024];
		int len =0;
		while((len = inputStream.read(buff))!=-1){
			outputStream.write(buff, 0, len);
		}
		
	}
	catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	finally {
		outputStream.flush();
		outputStream.close();
	}
}
public static void convert2png(String base, String path) throws IOException {
	BASE64Encoder encoder = new BASE64Encoder();
	String enbase = encoder.encode(base.getBytes());
	System.out.println(enbase);
	File fileFile = new File("D:/conver.png");
	if(fileFile.exists()) {
		fileFile.delete();
	}
	
	BASE64Decoder decoder = new BASE64Decoder();
	byte[] byt = decoder.decodeBuffer(enbase);
	for( int i=0, len = byt.length ; i<len; ++i) {
		if(byt[i]<=0) {
			byt[i] +=256;
		}
	}
	OutputStream outputStream =null;
	InputStream  inputStream = new ByteArrayInputStream(byt);
	try {
		outputStream = new FileOutputStream(fileFile);
		byte[] buff = new byte[1024];
		int len =0;
		while((len = inputStream.read(buff))!=-1){
			outputStream.write(buff, 0, len);
		}
		
	}
	catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	finally {
		outputStream.flush();
		outputStream.close();
	}
}
public static void saveByByte(byte[] byts, String path) throws IOException {
	System.out.println(byts.toString());
	File file = new File(path);
	if(file.exists()) {
		file.delete();
	}
	
	InputStream inputStream = new ByteArrayInputStream(byts);
	OutputStream outputStream= null;
	try {
		outputStream = new FileOutputStream(file);
		int len=0;
		byte[] buf = new byte[1024];
		while(( len = inputStream.read(buf)) != -1) {
			outputStream.write(buf, 0, len);
			System.out.println("写入1kb");
		}
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	finally {
		outputStream.flush();
		outputStream.close();
	}
}
}
