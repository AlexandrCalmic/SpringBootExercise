package com.koerber.supplychain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.koerber.supplychain.model.ArticleCategory.*;
import static org.junit.jupiter.api.Assertions.*;

class ArticleCategorizerImplTest {

    private ArticleCategorizerImpl articleCategorizer;

    @BeforeEach
    void setUp() {
        articleCategorizer = new ArticleCategorizerImpl();
    }

    @Test
    void categorizeArticle_largeHeavy() {
        assertEquals(LARGE_HEAVY, articleCategorizer.categorizeArticle(60.0, 150.0));
    }

    @Test
    void categorizeArticle_largeLight() {
        assertEquals(LARGE_LIGHT, articleCategorizer.categorizeArticle(30.0, 150.0));
    }

    @Test
    void categorizeArticle_smallHeavy() {
        assertEquals(SMALL_HEAVY, articleCategorizer.categorizeArticle(60.0, 50.0));
    }

    @Test
    void categorizeArticle_smallLight() {
        assertEquals(SMALL_LIGHT, articleCategorizer.categorizeArticle(30.0, 50.0));
    }
}
