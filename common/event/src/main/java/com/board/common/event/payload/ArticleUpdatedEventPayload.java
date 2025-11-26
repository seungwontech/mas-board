package com.board.common.event.payload;

import com.board.common.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdatedEventPayload implements EventPayload {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
