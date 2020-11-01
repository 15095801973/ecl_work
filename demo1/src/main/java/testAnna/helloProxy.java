package testAnna;

import java.lang.reflect.Method;

import testAnna.*;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class helloProxy {
private static Enhancer enhancer;
static {
	enhancer=new Enhancer();
	enhancer.setSuperclass(hello.class);
	enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				Interceptor  inter= method.getAnnotation(Interceptor.class);
				Object result;
				if (inter!=null) {
					System.out.println("begin"+method.getName());
					result = proxy.invokeSuper(obj, args);
					System.out.println("end"+method.getName());
					
				}
				else {
					result = proxy.invoke(obj, args);
				}
				return result;
			}
});
}
public static hello create(String name)
{
	return (hello) enhancer.create(new Class[] {},new Object[] {});
	
}
}
