package com.koerber.supplychain.controller;

import com.koerber.supplychain.model.Article;
import com.koerber.supplychain.service.ArticleManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleManagerController {

    private final ArticleManager articleManager;

    @GetMapping
    public List<Article> getAllArticles(){
        return articleManager.getAllArticles();
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id){
        return articleManager.getArticle(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createArticle(@Valid @RequestBody Article article){
        return articleManager.createArticle(article);
    }

    @PutMapping("/{id}")
    public Long updateArticle(@PathVariable Long id, @RequestBody Article article){
        return articleManager.updateArticle(id, article);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteArticle(@PathVariable Long id){
        return articleManager.deleteArticle(id);
    }

    @GetMapping("/{id}/category")
    public ResponseEntity<String> getArticleCategory(@PathVariable Long id){
        return ResponseEntity.ok(articleManager.getArticleCategory(id).getDescription());
    }

}
