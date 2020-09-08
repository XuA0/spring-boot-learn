package com.xuao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xuao.bean.AcctEntity;

@Repository
public interface AcctMapper {

	@Select("select * from op_acct")
	List<AcctEntity> getAll();
	
	@Select("select * from op_acct where acct_id = #{id}")
	AcctEntity getOne(String id);
}
