package com.board.article.entity;


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
@Table(name = "article")
public class Article {

    @Id
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static Article create(Long articleId, String title, String content, Long boardId, Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        return new Article(articleId, title, content, boardId, memberId, now, now);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
