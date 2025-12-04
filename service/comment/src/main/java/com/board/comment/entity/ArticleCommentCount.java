package com.board.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "article_comment_count")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCommentCount {
    @Id
    @Column(name = "article_id")
    private Long articleId; // shard key

    @Column(name ="comment_count")
    private Long commentCount;

    public static ArticleCommentCount init(Long articleId, Long commentCount) {
        ArticleCommentCount articleCommentCount = new ArticleCommentCount();
        articleCommentCount.articleId = articleId;
        articleCommentCount.commentCount = commentCount;
        return articleCommentCount;
    }
}
