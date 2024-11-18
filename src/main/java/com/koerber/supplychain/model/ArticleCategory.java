package com.koerber.supplychain.model;

import lombok.Getter;

@Getter
public enum ArticleCategory {
    LARGE_HEAVY("Large and heavy article"),
    LARGE_LIGHT("Large but light article"),
    SMALL_HEAVY("Small but heavy article"),
    SMALL_LIGHT("Small and light article");

    private final String description;

    ArticleCategory(String description) {
        this.description = description;
    }

}

