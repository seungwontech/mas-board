package com.board.hotarticle.api;

import com.board.hotarticle.dto.HotArticleRes;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class HotArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9004");

    @Test
    void readAllTest() {
        List<HotArticleRes> responses = restClient.get()
                .uri("/v1/hot-articles/articles/date/{dateStr}", "20251204")
                .retrieve()
                .body(new ParameterizedTypeReference<List<HotArticleRes>>() {
                });

        for (HotArticleRes response : responses) {
            System.out.println("response = " + response);
        }
    }
}
