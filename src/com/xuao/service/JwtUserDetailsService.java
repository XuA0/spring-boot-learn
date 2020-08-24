package com.xuao.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuao.bean.UserEntity;
import com.xuao.controller.HelloController;
import com.xuao.jwt.SecurityUserDetails;
import com.xuao.redis.RedisUtils;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	@Autowired
	RedisUtils redisUtils;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        System.out.println("JwtUserDetailsService:" + user);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new SecurityUserDetails(user,authorityList);
    }
    
    public boolean validateUser(UserEntity userEntity) {

		try {
			logger.info(new ObjectMapper().writeValueAsString(userEntity));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	boolean hasKey = redisUtils.exists(userEntity.getUsername());
    	if (hasKey) {
    		logger.info(userEntity.getPassword());
			Object object = redisUtils.get(userEntity.getUsername().toString());
    		if (object.toString().equals(userEntity.getPassword())) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
    }

}