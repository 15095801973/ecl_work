package IOC;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class myioc {

	public static HashMap<String, Object> beanFactory =new HashMap<String, Object>();
	public static HashMap<String, HashMap<String,String>> propertyFact =new HashMap<String, HashMap<String,String>>();
	public static void Init() {
		try {
			HashMap<String, Class> loadclasslist=load_file("src/main/resources/beans.xml");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Autowired();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)  {
		try {
			HashMap<String, Class> loadclasslist = load_file("src/main/resources/beans.xml");
			System.out.println(loadclasslist);
			new_instance(loadclasslist);
			System.out.println(beanFactory);
			try {
				Autowired();
				test();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void test()  {
		for(Map.Entry<String, Object> entry:beanFactory.entrySet()) {
			try {
				Method method=entry.getValue().getClass().getMethod("test");
				try {
					method.invoke(entry.getValue());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public static void new_instance(HashMap<String, Class> loadcasslist) {
		for(Map.Entry<String, Class> entry:loadcasslist.entrySet()) {
			try {
				beanFactory.put(entry.getKey(),entry.getValue().newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//通过扫描所有类进行注入
	public static void find(String packageName) throws Exception {
		File root = new File(System.getProperty("user.dir") + "\\src\\main\\java");
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
			Object obj = Class.forName(packageName + name);//不同于classload,直接获取对象
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
	//根据xml 配置文件 来加载类和 方法
	public static HashMap<String, Class> load_file(String file) throws ClassNotFoundException {
		HashMap<String, Class> loadclazzlist = new HashMap<>();
		   //1.创建Reader对象
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document;
		try {
			document = reader.read(new File(file));
		
        //3.获取根节点
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()){
            Element stu = (Element) iterator.next();
            List<Attribute> attributes = stu.attributes();
            String id=null;
            System.out.println("======获取类值======");
            for (Attribute attribute : attributes) {
            	
            	if(attribute.getName().equals("id"))
            	{
                    System.out.println("id:"+attribute.getValue());
                    id=attribute.getValue();
            	}
            	else if (attribute.getName().equals("class")) {
            		System.out.println("class:"+attribute.getValue());
            		//根据字符串加载类
            		Class<?> clazz=ClassLoader.getSystemClassLoader().loadClass(attribute.getValue());
            		Annotation[] annoList=clazz.getAnnotations();
            		System.out.println(annoList.length);
            		for(int i=0;i<annoList.length;i++) {
            			System.out.println(annoList[i].annotationType());
            			//只对有mycomponent注解的类初始化
            			if(annoList[i].annotationType()==mycomponent.class) {
            				System.out.println("put:"+id);
            		loadclazzlist.put(id, clazz);
            		//添加属性
            		HashMap<String, String> temp = new HashMap<String, String>();
            		Iterator<Element> iter1=stu.elementIterator();
            		while (iter1.hasNext()) {
						Element stu1 = (Element) iter1.next();
						List<Attribute> attributes1 = stu1.attributes();
			            String id1=null,v1=null;
			            System.out.println("======获取属性值======");
			            for (Attribute attribute1 : attributes1) {
			            	if(attribute1.getName().equals("name"))
			            	{
			                    System.out.println("name:"+attribute1.getValue());
			                    id1=attribute1.getValue();
			            	}
			            	else if(attribute1.getName().equals("value")){
			            		System.out.println("value:"+attribute1.getValue());
			                    v1=attribute1.getValue();
							}
			            }
			          //添加一个属性
			            temp.put(id1, v1);
            		
            			}//循环添加,最后添加到FACT
            		propertyFact.put(id, temp);
            		}
            			//至此对一个bean解析完毕
				}
            }
        }
		}
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loadclazzlist;
	}

	public static void Autowired() throws IllegalArgumentException, IllegalAccessException{
		for(Map.Entry<String, Object> entry:beanFactory.entrySet()) {
			Field[] fields=entry.getValue().getClass().getDeclaredFields();
			System.out.println("all field: "+fields);
			for(Field field:fields) {
				Annotation[] annotations=field.getAnnotations();
				for(Annotation anno:annotations) {
					System.out.println("field type"+field.getType().getTypeName());
					if(anno.annotationType()==myautowired.class) {
						System.out.println("field: "+field.getType().getTypeName());
						String value=propertyFact.get(entry.getKey()).get(field.getName());
						field.setAccessible(true);
						field.set(entry.getValue(), value);
					}
				}
			}
//			Method[] methods=entry.getValue().getClass().getDeclaredMethods();
//			for(Method method:methods) {
//				System.out.println("methodName="+method.getName());
//			}
		}
	}
}
