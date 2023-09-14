package com.woori.bookspring.constant;

public enum ArticleType {
    ARTICLE, REVIEW, NOTICE, EVENT, QUESTION;

    public static ArticleType getArticleType(String articleType) {
        return switch (articleType) {
            case "01" -> ARTICLE;
            case "02" -> REVIEW;
            case "03" -> NOTICE;
            case "04" -> EVENT;
            case "05" -> QUESTION;
            default -> null;
        };
    }
}
