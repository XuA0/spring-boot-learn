package com.xuao.bean;

import java.util.Date;

public class ArticleEntity {
	int articlesId;
	String articleTitle;
	String articleText;
	Date lastUpdateDate;
	String lastUpdateId;

	public int getArticlesId() {
		return articlesId;
	}

	public void setArticlesId(int articlesId) {
		this.articlesId = articlesId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleText() {
		return articleText;
	}

	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

}
