package test;

import java.io.File;
import java.lang.reflect.Method;

import IOC.myaction;
import IOC.myioc;

public class LoopApp {
	public static void main(String[] args) throws Exception {
		String packageName = "";
		File root = new File(System.getProperty("user.dir") + "\\src\\main\\java");
		loop(root, packageName);
	}
	public static void find(String packageName) throws Exception {
		File root = new File(System.getProperty("user.dir") + "\\src");
		loop(root, packageName);
	}
	public static void loop(File folder, String packageName) throws Exception {
		File[] files = folder.listFiles();
		for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
			File file = files[fileIndex];
			if (file.isDirectory()) {
				loop(file, packageName + file.getName() + ".");
			} else {
				listMethodNames(file.getName(), packageName);
			}
		}
	}

	public static void listMethodNames(String filename, String packageName) {
		try {
			String name = filename.substring(0, filename.length() - 5);
			System.out.println("pack = "+packageName);
			System.out.println("name = "+name);
			Object obj = Class.forName(packageName + name);
			Method[] methods = obj.getClass().getDeclaredMethods();
			System.out.println(filename);
			for (int i = 0; i < methods.length; i++) {
				System.out.println("\t" + methods[i].getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception = " + e.getLocalizedMessage());
		}
	}
}