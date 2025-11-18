package com.board.like.service;

import com.board.common.snowflake.Snowflake;
import com.board.like.dto.ArticleLikeReq;
import com.board.like.dto.ArticleLikeRes;
import com.board.like.entity.ArticleLike;
import com.board.like.entity.ArticleLikeCount;
import com.board.like.repository.ArticleLikeCountRepository;
import com.board.like.repository.ArticleLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleLikeService {

    Snowflake snowflake = new Snowflake();

    private final ArticleLikeRepository articleLikeRepository;

    private final ArticleLikeCountRepository articleLikeCountRepository;


    public ArticleLikeRes read(Long articleId, Long memberId) {
        ArticleLike articleLike = articleLikeRepository.findByArticleIdAndMemberId(articleId, memberId);
        return ArticleLikeRes.from(articleLike);
    }

    public void like(Long articleId, Long memberId) {
        ArticleLike createLike = ArticleLike.create(snowflake.nextId(), articleId, memberId);
        ArticleLike like = articleLikeRepository.save(createLike);

        int result = articleLikeCountRepository.increase(articleId);
        if (result == 0) {
            ArticleLikeCount init = ArticleLikeCount.init(articleId, 1L);
            articleLikeCountRepository.save(init);
        }
    }


    public void unlike(Long articleId, Long memberId) {
        ArticleLike like = articleLikeRepository.findByArticleIdAndMemberId(articleId, memberId);
        articleLikeRepository.delete(like);
        articleLikeCountRepository.decrease(articleId);
    }


    public Long count(Long articleId) {
        return articleLikeCountRepository.findById(articleId).map(ArticleLikeCount::getLikeCount).orElse(0L);
    }
}
