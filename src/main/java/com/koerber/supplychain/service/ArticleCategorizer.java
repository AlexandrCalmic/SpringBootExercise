package com.koerber.supplychain.service;

import com.koerber.supplychain.model.ArticleCategory;

public interface ArticleCategorizer {

    ArticleCategory categorizeArticle(Double weight, Double volume);
}
