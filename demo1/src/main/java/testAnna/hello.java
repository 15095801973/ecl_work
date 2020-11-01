package testAnna;

public class hello {
	public String name;
	
	public hello() {
		super();
	}
	@Interceptor
	public void say() {
		
		System.out.println("hello");
	}
}
