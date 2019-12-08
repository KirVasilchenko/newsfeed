package ru.rosbank.javaschool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import ru.rosbank.javaschool.repository.PostRepository;
import ru.rosbank.javaschool.entity.PostEntity;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class CrudApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApiApplication.class, args);
    }


    @Bean
    public CommandLineRunner runner(PostRepository repository) {
        return args -> repository.saveAll(List.of(
                new PostEntity(0, "POST 1", null, false, 0),
                new PostEntity(0, "POST 2", null, false, 0),
                new PostEntity(0, "POST 3", null, false, 0),
                new PostEntity(0, "POST 4", null, false, 0),
                new PostEntity(0, "POST 5", null, false, 0),
                new PostEntity(0, "POST 6", null, false, 0),
                new PostEntity(0, "POST 7", null, false, 0),
                new PostEntity(0, "POST 8", null, false, 0),
                new PostEntity(0, "POST 9", null, false, 0),
                new PostEntity(0, "POST 10", null, false, 0),
                new PostEntity(0, "SUPER POST 1", null, false, 0)
        ));
    }
}