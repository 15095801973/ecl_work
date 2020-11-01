package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试
 * 
 * @author 林计钦
 * @version 1.0 2013-11-4 下午10:56:04
 */
public class h2 {
    public  static void main(String[] args) {
        // 1、读取配置文件实例化一个IOC容器
        ApplicationContext context = new ClassPathXmlApplicationContext("/helloworld.xml");
        // 2、从容器中获取Bean，注意此处完全“面向接口编程，而不是面向实现”
        helloService he = context.getBean("hello", helloService.class);
        // 3、执行业务逻辑
        he.sayHello();
    }
}