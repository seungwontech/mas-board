package com.board.view.repository;

import com.board.view.entity.ArticleViewCount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleViewCountBackUpRepositoryTest {
    @Autowired
    ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void updateViewCountTest() {
        // Given
        articleViewCountBackUpRepository.save(ArticleViewCount.init(1L, 0L));
        entityManager.flush();
        entityManager.clear();

        // When
        // 동시성 상황에서 더 작은 값으로 덮어쓰지 않게 하는 방어 코드
        int result1 = articleViewCountBackUpRepository.updateViewCount(1L, 100L);
        int result2 = articleViewCountBackUpRepository.updateViewCount(1L, 300L);
        int result3 = articleViewCountBackUpRepository.updateViewCount(1L, 200L);

        // Then
        assertThat(result1).isEqualTo(1);
        assertThat(result2).isEqualTo(1);
        assertThat(result3).isEqualTo(0);

        ArticleViewCount articleViewCount = articleViewCountBackUpRepository.findById(1L).get();
        assertThat(articleViewCount.getViewCount()).isEqualTo(300L);


    }
}