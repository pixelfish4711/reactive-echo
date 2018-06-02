package com.boo.reactiveecho;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

import static java.time.Duration.ofMillis;

@RestController
public class Endpoint {

    private AtomicInteger counter = new AtomicInteger();

    private static final Logger LOGGER = LogManager.getLogger();

    @GetMapping("/hallo")
    public Mono<String> getDelayedEcho() {
        LOGGER.info("call /hallo " + counter.incrementAndGet());
        return Mono.just("Echo").delayElement(ofMillis(1000));
    }


}
