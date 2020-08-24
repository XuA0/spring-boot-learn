package com.xuao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xuao.bean.Person;

/***
 * 
 * @author P1323190 *
 */
@SpringBootApplication
@MapperScan("com.xuao.mapper")
public class HelloWorldMainApplication {
	

	public static void main(String[] args) {
		// Spring application start
		SpringApplication.run(HelloWorldMainApplication.class, args);
	}
	
	

}
