package com.board.hotarticle.service;

import com.board.hotarticle.repository.ArticleCommentCountRedisRepository;
import com.board.hotarticle.repository.ArticleLikeCountRedisRepository;
import com.board.hotarticle.repository.ArticleViewCountRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HotArticleScoreCalculatorService {

    private final ArticleLikeCountRedisRepository articleLikeCountRedisRepository;

    private final ArticleViewCountRedisRepository articleViewCountRedisRepository;

    private final ArticleCommentCountRedisRepository articleCommentCountRedisRepository;

    private static final long ARTICLE_LIKE_COUNT_WEIGHT = 3;
    private static final long ARTICLE_COMMENT_COUNT_WEIGHT = 2;
    private static final long ARTICLE_VIEW_COUNT_WEIGHT = 1;

    public long calculate(Long articleId) {
        Long articleLikeCount = articleLikeCountRedisRepository.read(articleId);
        Long articleViewCount = articleViewCountRedisRepository.read(articleId);
        Long articleCommentCount = articleCommentCountRedisRepository.read(articleId);

        return articleLikeCount * ARTICLE_LIKE_COUNT_WEIGHT
               + articleViewCount * ARTICLE_VIEW_COUNT_WEIGHT
               + articleCommentCount * ARTICLE_COMMENT_COUNT_WEIGHT;
    }
}
