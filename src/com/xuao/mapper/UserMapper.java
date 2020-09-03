package com.xuao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xuao.bean.AcctEntity;
import com.xuao.bean.UserEntity;

@Repository
public interface UserMapper {

	@Select("select * from user")
	List<AcctEntity> getAll();
	
	@Select("select * from user where username = #{id}")
	UserEntity getOne(String id);
}
