package com.koerber.supplychain.service;

import com.koerber.supplychain.model.ArticleCategory;
import org.springframework.stereotype.Component;

import static com.koerber.supplychain.model.ArticleCategory.*;

@Component
public class ArticleCategorizerImpl implements ArticleCategorizer {     // 4. extra component

    @Override
    public ArticleCategory categorizeArticle(Double weight, Double volume) {
        if (volume > 100 && weight > 50) {
            return LARGE_HEAVY;
        } else if (volume > 100) {
            return LARGE_LIGHT;
        } else if (weight > 50) {
            return SMALL_HEAVY;
        } else {
            return SMALL_LIGHT;
        }
    }
}
