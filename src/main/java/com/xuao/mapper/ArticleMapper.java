package com.xuao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.xuao.bean.ArticleEntity;

@Repository
public interface ArticleMapper {

	@Select("select * from articles")
	public List<ArticleEntity> getAllArticles();
	
	@Select("select * from articles where articlesId = #{articlesId}")
	public ArticleEntity getOneArticle(int articlesId);
	
	@Insert("INSERT INTO `articles`(`articleTitle`, `articleText`, `lastUpdateDate`, `lastUpdateId`) VALUES (#{articleTitle},#{articleText},#{lastUpdateDate},#{lastUpdateId})")
	@Options(useGeneratedKeys = true, keyColumn = "articlesId", keyProperty = "articlesId")
	public boolean writeArticle(ArticleEntity articleEntity);
	
	@Delete("DELETE FROM `articles` where articlesId = #{articlesId}")
	public boolean deleteArticle(ArticleEntity articleEntity);
}
