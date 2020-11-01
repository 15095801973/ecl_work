package IOC;

import IOC.myautowired;
import IOC.mycomponent;
import testAnna.Interceptor;

@mycomponent
public class Person {
	@myautowired
	public String name;
	public void setName(String name) {
		this.name=name;
	}
	
	public void test() {
		System.out.println("Person.test()");
		System.out.println(name);
	}
}
