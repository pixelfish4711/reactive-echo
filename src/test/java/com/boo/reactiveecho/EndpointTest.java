package com.boo.reactiveecho;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;


@ExtendWith(SpringExtension.class)
@WebFluxTest
class EndpointTest {

    private static final Logger LOGGER = LogManager.getLogger();

    private WebClient webClient = WebClient.builder().build();

    @Test
    void getEcho() throws InterruptedException {

        AtomicInteger counter = new AtomicInteger();

        Flux.range(0, 3000)
                .publishOn(Schedulers.parallel())
                .subscribe(s ->
                        webClient.get().uri("http://localhost:8000/hallo")
                                .retrieve()
                                .bodyToMono(String.class)
                                .subscribe(b -> LOGGER.info("result: {}, counter:{}", b, counter.incrementAndGet()))
                );

        Thread.sleep(10000);

    }

}