package com.xuao.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuao.bean.UserEntity;
import com.xuao.jwt.JwtTokenUtil;

@RestController
public class LoginController {
	
	Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

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
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(sysUser.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return token;
	}

	@PostMapping("haha")
	public String haha() {
		UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return "haha:" + userDetails.getUsername() + "," + userDetails.getPassword();
	}
}