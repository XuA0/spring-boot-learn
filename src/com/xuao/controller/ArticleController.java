package com.xuao.controller;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.naming.java.javaURLContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xuao.bean.ArticleEntity;
import com.xuao.mapper.ArticleMapper;

@RestController
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	ArticleMapper articleMapper;
	
	@RequestMapping("/getAllArticles")
	public List<ArticleEntity> getAllArticles() {
		return articleMapper.getAllArticles();
	}
	
	@RequestMapping("/getAllArticles2")
	public ModelAndView getAllArticles2() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("articles", articleMapper.getAllArticles());
		modelAndView.setViewName("common/index");
		
		return modelAndView;
	}
	
	@RequestMapping("/getOneArticle")
	public ModelAndView getAllArticles(int articlesId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("articles", articleMapper.getAllArticles());
		modelAndView.addObject("article", articleMapper.getOneArticle(articlesId));
		
		modelAndView.setViewName("article/article");
		return modelAndView;
	}
	
	@GetMapping("/writeArticle")
	public ModelAndView writeArticle() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("articles", articleMapper.getAllArticles());
		
		modelAndView.setViewName("article/writeArticle");
		return modelAndView;
	}
	
	@PostMapping("/writeArticle")
	public ModelAndView writeArticle(@RequestBody ArticleEntity articleEntity) {
		
		articleEntity.setLastUpdateDate(new Date(java.lang.System.currentTimeMillis()));
		articleEntity.setLastUpdateId("xuao");
		articleMapper.writeArticle(articleEntity);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("articles", articleMapper.getAllArticles());
		
		modelAndView.setViewName("article/writeArticle");
		return modelAndView;
	}
}
