package com.board.view.entity;

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
@Table(name="article_view_count")
@Getter
@Entity
public class ArticleViewCount {

    @Id
    @Column(name = "article_id")
    private Long articleId;

    @Column(name ="view_count")
    private Long viewCount;


    public static ArticleViewCount init(Long articleId, Long viewCount) {
        return new ArticleViewCount(articleId, viewCount);
    }

}
