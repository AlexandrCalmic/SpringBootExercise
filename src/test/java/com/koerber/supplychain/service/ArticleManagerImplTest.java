package com.koerber.supplychain.service;

import com.koerber.supplychain.exception.ArticleAlreadyExistsException;
import com.koerber.supplychain.exception.ArticleNotFoundException;
import com.koerber.supplychain.model.Article;
import com.koerber.supplychain.model.ArticleCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleManagerImplTest {

    @Mock
    private ArticleCategorizer articleCategorizer;

    @InjectMocks
    private ArticleManagerImpl articleManagerImpl;

    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article(1L, "Test Article", 10.0, 20.0);
    }

    @Test
    void testCreateArticle() {
        // Act
        Long createdArticleId = articleManagerImpl.createArticle(article);

        // Assert
        assertNotNull(createdArticleId, "Article ID should not be null");
        assertEquals(1L, createdArticleId, "Article ID should be the same as the one assigned");
    }

    @Test
    void shouldThrowArticleAlreadyExistsExceptionWhenArticleExists() {
        // Arrange
        Article article = new Article(1L, "Test Article", 10.0, 20.0);
        articleManagerImpl.createArticle(article);

        // Act & Assert
        Article duplicateArticle = new Article(1L, "Duplicate Article", 15.0, 25.0);
        ArticleAlreadyExistsException exception = assertThrows(
                ArticleAlreadyExistsException.class,
                () -> articleManagerImpl.createArticle(duplicateArticle)
        );

        assertEquals(HttpStatus.CONFLICT , exception.getStatusCode(), "Article with this ID already exists");
    }

    @Test
    void testGetAllArticles() {
        // Act
        articleManagerImpl.createArticle(article);
        List<Article> allArticles = articleManagerImpl.getAllArticles();

        // Assert
        assertEquals(1, allArticles.size(), "There should be exactly 1 article");
        assertEquals(article, allArticles.get(0), "The created article should be the first in the list");
    }

    @Test
    void testGetArticle() {
        // Arrange
        articleManagerImpl.createArticle(article);

        // Act
        Article fetchedArticle = articleManagerImpl.getArticle(1L);

        // Assert
        assertNotNull(fetchedArticle, "Fetched article should not be null");
        assertEquals(article, fetchedArticle, "Fetched article should be the same as the created one");
    }

    @Test
    void testGetArticleNotFound() {
        // Act & Assert
        ArticleNotFoundException exception = assertThrows(ArticleNotFoundException.class, () -> {
            articleManagerImpl.getArticle(999L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Status should be 404");
        assertEquals("Article not found", exception.getReason(), "The error message should be 'Article not found'");
    }

    @Test
    void testUpdateArticle() {
        // Arrange
        articleManagerImpl.createArticle(article);

        // Act
        Article updatedArticle = new Article(1L, "Updated Article", 15.0, 25.0);
        Long updatedArticleId = articleManagerImpl.updateArticle(1L, updatedArticle);

        // Assert
        assertNotNull(updatedArticleId, "Updated article ID should not be null");
        assertEquals(1L, updatedArticleId, "Updated article ID should be the same as the original one");

        Article fetchedUpdatedArticle = articleManagerImpl.getArticle(1L);
        assertEquals("Updated Article", fetchedUpdatedArticle.getDescription(), "Article description should be updated");
        assertEquals(15.0, fetchedUpdatedArticle.getWeight(), "Article weight should be updated");
    }

    @Test
    void testDeleteArticle() {
        // Arrange
        articleManagerImpl.createArticle(article);

        // Act
        Boolean isDeleted = articleManagerImpl.deleteArticle(1L);

        // Assert
        assertTrue(isDeleted, "The article should be deleted successfully");
        assertThrows(ResponseStatusException.class, () -> articleManagerImpl.getArticle(1L), "Deleted article should not be found");
    }

    @Test
    void testGetArticleCategory() {
        // Arrange
        articleManagerImpl.createArticle(article);
        ArticleCategory expectedCategory = ArticleCategory.SMALL_LIGHT;
        when(articleCategorizer.categorizeArticle(article.getWeight(), article.getVolume())).thenReturn(expectedCategory);

        // Act
        ArticleCategory category = articleManagerImpl.getArticleCategory(1L);

        // Assert
        assertNotNull(category, "Article category should not be null");
        assertEquals(expectedCategory, category, "The category returned should match the expected one");
    }
}
