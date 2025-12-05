package com.board.articleread.service.eventhandler;

import com.board.common.event.EventPayload;
import com.board.articleread.repository.ArticleQueryModelRepository;
import com.board.common.event.Event;
import com.board.common.event.EventType;
import com.board.common.event.payload.CommentDeletedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDeletedEventHandler implements EventHandler {
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        CommentDeletedEventPayload payload = (CommentDeletedEventPayload) event.getPayload();
        articleQueryModelRepository.read(payload.getArticleId())
                .ifPresent(articleQueryModel -> {
                    articleQueryModel.updateBy(payload);
                    articleQueryModelRepository.update(articleQueryModel);
                });
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.COMMENT_DELETED == event.getType();
    }
}
