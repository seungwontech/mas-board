package com.board.comment.entity;


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
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public static Comment create(Long commentId, String content, Long parentCommentId, Long memberId, Long articleId) {
        LocalDateTime now = LocalDateTime.now();
        Long returnParentCommentId = parentCommentId == null ? commentId : parentCommentId;
        return new Comment(commentId, content, returnParentCommentId, memberId, articleId, false, now, now);
    }

    public boolean isRoot() {
        return parentCommentId.longValue() == commentId;
    }

    public void delete() {
        deleted = true;
    }

    public void update(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
