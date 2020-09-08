package com.xuao.controller;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xuao.bean.ResultVO;
import com.xuao.mapper.UserMapper;
import com.xuao.redis.RedisUtils;

@RestController
@RequestMapping("/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	RedisUtils redisUtils;
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("/getOneUser")
	public ResultVO getOneUser(String username) {
		boolean hasKey = redisUtils.exists(username);
		String str = "";
		if (hasKey) {
			Object object = redisUtils.get(username);
			logger.info("从缓存获取的数据" + object);
			str = object.toString();
			logger.info("从缓存删除的数据" + object);
			redisUtils.remove(username);
		} else {
			logger.info("从数据库中获取数据");
			str = userMapper.getOne(username).getUsername();
			redisUtils.set(username, str, 60L, TimeUnit.SECONDS);
			logger.info("数据插入缓存" + str);
		}
		
		return new ResultVO(str);
	}
}
