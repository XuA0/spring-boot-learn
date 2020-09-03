package com.xuao.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xuao.bean.AcctEntity;
import com.xuao.bean.Person;
import com.xuao.mapper.AcctMapper;
import com.xuao.redis.RedisUtils;

@RestController
public class HelloController {

	Logger logger = LoggerFactory.getLogger(HelloController.class);

	@Autowired
	AcctMapper acctMapper;

	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}

	@Autowired
	Person person;

	@RequestMapping("/test")
	public Person test() {
		logger.info("I am in test");
		return person;
	}

	@RequestMapping("/getAllAccts")
	public List<AcctEntity> getAllAccts() {
		return acctMapper.getAll();
	}

	@RequestMapping("/getOneAcct")
	public String getOneAcct(String acctId) {
		// 查询缓存中是否存在
		boolean hasKey = redisUtils.exists(acctId);
		String str = "";
		if (hasKey) {
			// 获取缓存
			Object object = redisUtils.get(acctId);
			logger.info("从缓存获取的数据" + object);
			str = object.toString();
			logger.info("从缓存删除的数据" + object);
			redisUtils.remove(acctId);
		} else {
			// 从数据库中获取信息
			logger.info("从数据库中获取数据");
			str = acctMapper.getOne(acctId).getAcct_id();
			// 数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
			redisUtils.set(acctId, str, 60L, TimeUnit.SECONDS);
			logger.info("数据插入缓存" + str);
		}

		return str;
	}

	@RequestMapping("/getExpire")
	public String getExpire(String acctId) {
		// 查询缓存中是否存在
		boolean hasKey = redisUtils.exists(acctId);
		String str = "";
		if (hasKey) {
			// 获取缓存
			long expireTime = redisUtils.getExpire(acctId, TimeUnit.SECONDS);
			logger.info("从缓存获取的数据" + expireTime);
			str = new Long(expireTime).toString();
		} else {
			str = "缓存中不存在该数据或该数据已超时";
		}

		return str;
	}

	@GetMapping("/getUser")
	public ModelAndView index() {
		return new ModelAndView("common/index", "username", "xuao");
	}

	@Autowired
	private RedisUtils redisUtils;

}
