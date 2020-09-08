package com.xuao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.xuao.alipay.AlipayProperties;

///**
// * 配置文件监听器，用来加载自定义配置文件
// */

@Configuration  
@PropertySources(  
    {  
            @PropertySource("alipay.properties"),  
    }  
)  
public class PropertyConfiguration {  
	Logger logger = LoggerFactory.getLogger(PropertyConfiguration.class);

    @Bean  
    @ConfigurationProperties(prefix = "alipay")  
    public AlipayProperties alipayProperties() {  
    	logger.info("load alipay properties");
    	AlipayProperties.loadProperties();
    	
    	return new AlipayProperties();
    }  
   
} 