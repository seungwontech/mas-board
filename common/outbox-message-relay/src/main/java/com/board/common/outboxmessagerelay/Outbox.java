package com.board.common.outboxmessagerelay;

import com.board.common.event.EventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "outbox")
public class Outbox {
    @Id
    @Column(name = "outbox_id")
    private Long outboxId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Column(name = "payload", length = 5000)
    private String payload;

    @Column(name = "shard_key")
    private Long shardKey;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static Outbox create(Long outboxId, EventType eventType, String payload, Long shardKey) {
        return new Outbox(outboxId, eventType, payload, shardKey, LocalDateTime.now());
    }

}
