package com.guard.WeChat.responseMessage;

import java.util.List;
/**
 * 回复图文消息类
 * @author guard
 * @version 2016年1月4日15:08:47
 */
public class ArticlesMessageP extends BaseMessageP {
	// 图文消息个数，限制为10条以内
	private String ArticleCount;
	// 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	private List<Article> Articles;

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
 
}
