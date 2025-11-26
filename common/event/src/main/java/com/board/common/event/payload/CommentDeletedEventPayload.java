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
public class CommentDeletedEventPayload implements EventPayload {
    private Long commentId;
    private String content;
    private String path;
    private Long articleId;
    private Long memberId;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private Long articleCommentCount;
}
