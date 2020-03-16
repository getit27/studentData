package cn.edu.nju.studentdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class StudentDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentDataApplication.class, args);
	}

}
