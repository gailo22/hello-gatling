package com.gailo22.gatling.hellogatling;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @GetMapping("/api/hello")
    public String hello() {
        return "hello";
    }

    @Value("${path.hello}")
    private String textPath;

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
        Path path = resolvePath();
        Stream<String> lines = Files.lines(path);
        List<String> collect = lines.map(x -> x.split("=")[1])
            .limit(1)
            .collect(Collectors.toList());

        return collect.get(0);
    }

    public Path resolvePath() throws IOException {
        if (textPath.startsWith("classpath:")) {
            String path = textPath.split(":")[1];
            File file = new ClassPathResource(path + "/hello.txt").getFile();
            return file.toPath();
        } else {
            return Paths.get(textPath + "/hello.txt");
        }
    }
}

@Data
class HelloResponse {
    String message;
}
