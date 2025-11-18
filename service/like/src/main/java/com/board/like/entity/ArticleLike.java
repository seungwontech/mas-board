package com.board.like.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "article_like")
@Entity
public class ArticleLike {

    @Id
    @Column(name = "article_like_id")
    private Long articleLikeId;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static ArticleLike create(Long likeId, Long articleId, Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        return new ArticleLike(likeId, articleId, memberId, now);
    }
}
