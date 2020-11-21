package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class helloServiceImp implements helloService{
	@Override
	@Autowired
    public void sayHello(){
        System.out.println("Hello World!");
    }
}