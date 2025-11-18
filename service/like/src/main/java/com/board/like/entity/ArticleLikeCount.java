package com.board.like.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "article_like_count")
@Entity
public class ArticleLikeCount {

    @Id
    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "like_count")
    private Long likeCount;

    public void increase() {
        this.likeCount++;
    }

    public void decrease() {
        this.likeCount--;
    }

    public static ArticleLikeCount init(Long articleId, Long likeCount) {
        return new ArticleLikeCount(articleId, likeCount);
    }

}
