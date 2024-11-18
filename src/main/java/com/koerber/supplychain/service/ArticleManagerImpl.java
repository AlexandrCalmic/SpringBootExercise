package com.koerber.supplychain.service;


import com.koerber.supplychain.exception.ArticleAlreadyExistsException;
import com.koerber.supplychain.exception.ArticleNotFoundException;
import com.koerber.supplychain.model.Article;
import com.koerber.supplychain.model.ArticleCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleManagerImpl implements ArticleManager {

    private final ArticleCategorizer articleCategorizer;  // 4. constructor dependency injection

    public final List<Article> articles = new ArrayList<>();

    @Override
    public List<Article> getAllArticles(){
        return articles;
    }

    @Override
    public Article getArticle(Long id){
        return articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ArticleNotFoundException(HttpStatus.NOT_FOUND, "Article not found"));
    }

    @Override
    public Long createArticle(Article article){
        articles.stream()
                .filter(existingArticle -> existingArticle.getId().equals(article.getId()))
                .findAny()
                .ifPresent(existing -> {
                    throw new ArticleAlreadyExistsException(HttpStatus.CONFLICT, "An article with this ID already exists.");
                });
        articles.add(article);
        log.info("Created article: {}", article);
        return article.getId();
    }

    @Override
    public Long updateArticle(Long id, Article article){
        Optional<Article> exitingArticle = articles.stream().filter(a -> a.getId().equals(id)).findFirst();
        if (exitingArticle.isPresent()) {
            exitingArticle.get().setDescription(article.getDescription());
            exitingArticle.get().setWeight(article.getWeight());
            exitingArticle.get().setVolume(article.getVolume());
            log.info("Updated article: {}", article);
            return exitingArticle.get().getId();
        }
        return null;
    }

    @Override
    public Boolean deleteArticle(Long id){
        return articles.removeIf(article -> article.getId().equals(id));
    }

    @Override
    public ArticleCategory getArticleCategory(Long id){
        Article article = getArticle(id);
        return articleCategorizer.categorizeArticle(article.getWeight(), article.getVolume());
    }

}
