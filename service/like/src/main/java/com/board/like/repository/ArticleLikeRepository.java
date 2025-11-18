package com.board.like.repository;

import com.board.like.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    ArticleLike findByArticleIdAndMemberId(Long articleId, Long memberId);
}
