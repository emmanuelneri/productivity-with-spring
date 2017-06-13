package br.com.emmanuelneri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan
@ComponentScan
@EnableJpaRepositories
public class AppConfig {

    public static void main(String[] args) {
       SpringApplication.run(AppConfig.class, args);
    }

}
