package com.board.hotarticle.service;

import com.board.hotarticle.repository.ArticleCommentCountRedisRepository;
import com.board.hotarticle.repository.ArticleLikeCountRedisRepository;
import com.board.hotarticle.repository.ArticleViewCountRedisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.random.RandomGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class HotArticleScoreCalculatorServiceTest {
    @InjectMocks
    HotArticleScoreCalculatorService hotArticleScoreCalculatorService;

    @Mock
    ArticleLikeCountRedisRepository articleLikeCountRedisRepository;

    @Mock
    ArticleViewCountRedisRepository articleViewCountRedisRepository;

    @Mock
    ArticleCommentCountRedisRepository articleCommentCountRedisRepository;

    @Test
    void calculateScore() {
        // Given
        Long articleId = 1L;

        long likeCount = RandomGenerator.getDefault().nextLong(100);
        long viewCount = RandomGenerator.getDefault().nextLong(100);
        long commentCount = RandomGenerator.getDefault().nextLong(100);

        given(articleLikeCountRedisRepository.read(articleId)).willReturn(likeCount);
        given(articleViewCountRedisRepository.read(articleId)).willReturn(viewCount);
        given(articleCommentCountRedisRepository.read(articleId)).willReturn(commentCount);

        // When
        long score = hotArticleScoreCalculatorService.calculate(articleId);

        // Then
        assertThat(score).isEqualTo(3 * likeCount + 2 * commentCount + 1 * viewCount);

    }

}