package com.board.like.service;

import com.board.common.snowflake.Snowflake;
import com.board.like.dto.ArticleLikeRes;
import com.board.like.entity.ArticleLike;
import com.board.like.entity.ArticleLikeCount;
import com.board.like.repository.ArticleLikeCountRepository;
import com.board.like.repository.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleLikeService {

    Snowflake snowflake = new Snowflake();

    private final ArticleLikeRepository articleLikeRepository;

    private final ArticleLikeCountRepository articleLikeCountRepository;


    public ArticleLikeRes read(Long articleId, Long memberId) {
        ArticleLike articleLike = articleLikeRepository.findByArticleIdAndMemberId(articleId, memberId).orElseThrow();
        return ArticleLikeRes.from(articleLike);
    }

    @Transactional
    public void like(Long articleId, Long memberId) {
        ArticleLike createLike = ArticleLike.create(snowflake.nextId(), articleId, memberId);
        articleLikeRepository.save(createLike);

        int result = articleLikeCountRepository.increase(articleId);
        if (result == 0) {
            ArticleLikeCount init = ArticleLikeCount.init(articleId, 1L);
            articleLikeCountRepository.save(init);
        }
    }

    @Transactional
    public void unlike(Long articleId, Long memberId) {
        articleLikeRepository.findByArticleIdAndMemberId(articleId, memberId)
                .ifPresent(articleLike -> {
                    articleLikeRepository.delete(articleLike);
                    articleLikeCountRepository.decrease(articleId);
                });
    }


    public Long count(Long articleId) {
        return articleLikeCountRepository.findById(articleId)
                .map(ArticleLikeCount::getLikeCount)
                .orElse(0L);
    }

    @Transactional
    public void likePessimisticLock(Long articleId, Long memberId) {
        ArticleLike articleLike = ArticleLike.create(snowflake.nextId(), articleId, memberId);

        articleLikeRepository.save(articleLike);

        ArticleLikeCount articleLikeCount = articleLikeCountRepository.findLockedByArticleId(articleId)
                .orElseGet(() -> articleLikeCountRepository.save(
                                ArticleLikeCount.init(articleId, 0L)
                        )
                );
        articleLikeCount.increase();
    }

    @Transactional
    public void unlikePessimisticLock(Long articleId, Long memberId) {
        articleLikeRepository.findByArticleIdAndMemberId(articleId, memberId)
                .ifPresent(articleLike -> {
                    articleLikeRepository.delete(articleLike);
                    ArticleLikeCount articleLikeCount = articleLikeCountRepository.findLockedByArticleId(articleId)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다."));
                    articleLikeCount.decrease();
                });
    }


}
