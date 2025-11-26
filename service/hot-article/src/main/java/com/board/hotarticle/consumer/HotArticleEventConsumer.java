package com.board.hotarticle.consumer;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.hotarticle.service.HotArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotArticleEventConsumer {

    private final HotArticleService hotArticleService;

    @KafkaListener(topics = {
            EventType.Topic.BOARD_ARTICLE,
            EventType.Topic.BOARD_LIKE,
            EventType.Topic.BOARD_VIEW,
            EventType.Topic.BOARD_COMMENT
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[HotArticleEventConsumer.listen] received message ={}", message);
        Event<EventPayload> event = Event.fromJson(message);

        if (event != null) {
            hotArticleService.handleEvent(event);
        }
        ack.acknowledge();
    }


}
