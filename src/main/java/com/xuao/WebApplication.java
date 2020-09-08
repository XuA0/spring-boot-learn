package com.xuao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * 
 * @author Xu ao *
 */
@SpringBootApplication
@MapperScan("com.xuao.mapper")
public class WebApplication {

	public static void main(String[] args) {
		// Spring application start
		SpringApplication.run(WebApplication.class, args);
	}

}
