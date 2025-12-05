package com.board.articleread.service.eventhandler;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;

public interface EventHandler {

    void handle(Event<? extends EventPayload> event);

    boolean supports(Event<? extends EventPayload> event);

}
