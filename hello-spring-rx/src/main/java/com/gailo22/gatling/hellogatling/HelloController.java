package com.gailo22.gatling.hellogatling;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class HelloController {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private PathService pathService;

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


    @GetMapping("/api/get/hello")
    public String getHello() throws IOException {
        Path path = pathService.getResolvedPath();
        Stream<String> lines = Files.lines(path);
        List<String> collect = lines.map(x -> x.split("=")[1])
            .limit(1)
            .collect(Collectors.toList());

        return collect.get(0);
    }

}

@Data
class HelloResponse {
    String message;
}
