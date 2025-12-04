package com.board.hotarticle.service.eventHandler;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;

public interface EventHandler {

    boolean supports(Event<? extends EventPayload> event);

    void handle(Event<? extends EventPayload> event);

    Long findArticleId(Event<? extends EventPayload> event);
}
