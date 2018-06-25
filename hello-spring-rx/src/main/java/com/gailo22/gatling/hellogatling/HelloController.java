package com.gailo22.gatling.hellogatling;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class HelloController {

    @Autowired
    private ExecutorService executorService;

    @GetMapping("/api/hello")
    public String hello() {
        return "hello";
    }

    @Async
    @GetMapping("/api/hello-async")
    public CompletableFuture<String> helloAsync() {
        return CompletableFuture.supplyAsync(this::callApi, executorService);
    }

    private String callApi() {
        int nextInt = new Random().nextInt(2);
        try {
            TimeUnit.SECONDS.sleep(nextInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.valueOf(nextInt);
    }

    @GetMapping("/api/hello-rx")
    public Flux<String> helloRx() {
        return Flux.just("hello");
    }
}

@Data
class HelloResponse {
    String message;
}
