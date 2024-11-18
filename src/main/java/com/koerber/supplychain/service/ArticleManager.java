package com.koerber.supplychain.service;

import com.koerber.supplychain.model.Article;
import com.koerber.supplychain.model.ArticleCategory;

import java.util.List;

public interface ArticleManager {

    List<Article> getAllArticles();

    Article getArticle(Long id);

    Long createArticle(Article article);

    Long updateArticle(Long id, Article article);

    Boolean deleteArticle(Long id);

    ArticleCategory getArticleCategory(Long id);
}
