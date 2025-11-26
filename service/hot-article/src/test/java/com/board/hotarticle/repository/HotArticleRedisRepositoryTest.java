package com.board.hotarticle.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HotArticleRedisRepositoryTest {

    @Autowired
    private HotArticleRedisRepository hotArticleRedisRepository;

    @Test
    void addTest() throws InterruptedException {
        // Give
        LocalDateTime time = LocalDateTime.of(2025, 11, 25, 0, 0);

        long limit = 3;
        // When
        hotArticleRedisRepository.add(1L, time, 2L, limit, Duration.ofSeconds(3));
        hotArticleRedisRepository.add(2L, time, 3L, limit, Duration.ofSeconds(3));
        hotArticleRedisRepository.add(3L, time, 1L, limit, Duration.ofSeconds(3));
        hotArticleRedisRepository.add(4L, time, 5L, limit, Duration.ofSeconds(3));
        hotArticleRedisRepository.add(5L, time, 4L, limit, Duration.ofSeconds(3));

        // Then
        List<Long> articleIds = hotArticleRedisRepository.readAll("20251125");

        assertThat(articleIds).hasSize(Long.valueOf(limit).intValue());
        assertThat(articleIds.get(0)).isEqualTo(4);
        assertThat(articleIds.get(1)).isEqualTo(5);
        assertThat(articleIds.get(2)).isEqualTo(2);

        TimeUnit.SECONDS.sleep(5);

        assertThat(hotArticleRedisRepository.readAll("20251125")).isEmpty();

    }
}