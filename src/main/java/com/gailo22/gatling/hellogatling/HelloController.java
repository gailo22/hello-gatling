package com.gailo22.gatling.hellogatling;


import lombok.Data;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class HelloController {


    @GetMapping("/api/hello")
    public String hello() {
        return "hello";
    }

    @Async
    @GetMapping("/api/hello-async")
    public CompletableFuture<String> helloAsync() {
        return CompletableFuture.supplyAsync(this::callApi);
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
}

@Data
class HelloResponse {
    String message;
}
