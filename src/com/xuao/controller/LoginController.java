package com.xuao.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuao.bean.UserEntity;
import com.xuao.jwt.JwtTokenUtil;
import com.xuao.redis.RedisUtils;
import com.xuao.service.JwtUserDetailsService;

@RestController
public class LoginController {
	
	Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	RedisUtils redisUtils;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public String login(@RequestBody UserEntity sysUser, HttpServletRequest request) {
		
		try {
			log.info(new ObjectMapper().writeValueAsString(sysUser));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean validateUserSucc = jwtUserDetailsService.validateUser(sysUser);
		
		if (validateUserSucc) {
			final UserDetails userDetails = userDetailsService.loadUserByUsername(sysUser.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);
			return token;
		}else {
			return "user validate failed";
		}
	}
	
	@PostMapping("/saveUser")
	public void saveUser(@RequestBody UserEntity userEntity) {
		log.info("save user");
		try {
			log.info(new ObjectMapper().writeValueAsString(userEntity));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		redisUtils.set(userEntity.getUsername(), userEntity.getPassword());
	}
	

	@PostMapping("haha")
	public String haha() {
		UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return "haha:" + userDetails.getUsername() + "," + userDetails.getPassword();
	}
}