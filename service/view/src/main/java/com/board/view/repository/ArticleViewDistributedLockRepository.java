package com.board.view.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ArticleViewDistributedLockRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY_FORMAT = "view::article::%s::user::%s::lock";


    public boolean lock(Long articleId, Long memberId, Duration ttl) {
        String key = generateKey(articleId, memberId);
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "", ttl));
    }

    private String generateKey(Long articleId, Long memberId) {
        return KEY_FORMAT.formatted(articleId, memberId);
    }

}
