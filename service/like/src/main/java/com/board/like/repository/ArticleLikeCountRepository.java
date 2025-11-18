package com.board.like.repository;

import com.board.like.entity.ArticleLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleLikeCountRepository extends JpaRepository<ArticleLikeCount, Long> {

    @Query(
            value = "update article_like_count set like_count = like_count + 1 where article_id = :article_id",
            nativeQuery = true
    )
    int increase(@Param("articleId") Long articleId);

    @Query(
            value = "update article_like_count set like_count = like_count - 1 where article_id = :article_id",
            nativeQuery = true
    )
    int decrease(@Param("articleId") Long articleId);
}
