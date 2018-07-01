package com.gailo22.gatling.hellogatling;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PathService {

    @Value("${path.hello}")
    private String textPath;

    private Path resolvedPath;

    public Path getResolvedPath() {
        return resolvedPath;
    }

    @PostConstruct
    private void resolvePath() throws IOException {
        if (textPath.startsWith("classpath:")) {
            String path = textPath.split(":")[1];
            File file = new ClassPathResource(path + "/hello.txt").getFile();
            this.resolvedPath = file.toPath();
        }

        this.resolvedPath = Paths.get(textPath + "/hello.txt");
    }
}
