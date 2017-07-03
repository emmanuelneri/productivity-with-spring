package br.com.emmanuelneri;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.util.YearMonthJpaConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = {Bill.class, Jsr310JpaConverters.class, YearMonthJpaConverter.class})
@ComponentScan
@EnableJpaRepositories
@EnableCaching
public class AppConfig {

    public static void main(String[] args) {
       SpringApplication.run(AppConfig.class, args);
    }

}
