package com.board.like.api;

import com.board.like.dto.ArticleLikeRes;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticleLikeApiTest {

    RestClient restClient = RestClient.create("http://localhost:9005");


    @Test
    void likeAndUnlikeTest() {
        Long articleId = 9999L;

        like(articleId, 1L, "nopess");
        like(articleId, 2L, "nopess");
        ArticleLikeRes response1 = read(articleId, 1L);
        ArticleLikeRes response2 = read(articleId, 2L);
        System.out.println("response1 = " + response1);
        System.out.println("response2 = " + response2);

        unlike(articleId, 1L, "nopess");
        unlike(articleId, 2L, "nopess");
    }

    ArticleLikeRes read(Long articleId, Long memberId) {
        return restClient.get()
                .uri("/v1/article-likes/articles/{articleId}/members/{memberId}", articleId, memberId)
                .retrieve()
                .body(ArticleLikeRes.class);
    }


    void like(Long articleId, Long memberId, String lockType) {
        restClient.post()
                .uri("/v1/article-likes/articles/{articleId}/members/{memberId}/like/{lockType}", articleId, memberId, lockType)
                .retrieve()
                .toBodilessEntity();
    }

    void unlike(Long articleId, Long memberId, String lockType) {
        restClient.delete()
                .uri("/v1/article-likes/articles/{articleId}/members/{memberId}/unlike/{lockType}", articleId, memberId, lockType)
                .retrieve()
                .toBodilessEntity();
    }

    @Test
    void likePerformanceTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        likePerformanceTest(executorService, 1111L, "pess");
        likePerformanceTest(executorService, 2222L, "nopess");
    }

    void likePerformanceTest(ExecutorService executorService, Long articleId, String lockType) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3000);
        System.out.println(lockType + " start");

        like(articleId, 1L, lockType);

        long start = System.nanoTime();
        for(int i=0; i < 3000; i++) {
            long userId = i + 2;
            executorService.submit(() -> {
                like(articleId, userId, lockType);
                latch.countDown();
            });
        }

        latch.await();

        long end = System.nanoTime();

        System.out.println("lockType = " + lockType + ", time = " + (end - start) / 1000000 + "ms");
        System.out.println(lockType + " end");

        Long count = restClient.get()
                .uri("/v1/article-likes/articles/{articleId}/count", articleId)
                .retrieve()
                .body(Long.class);

        System.out.println("count = " + count);
    }
}
