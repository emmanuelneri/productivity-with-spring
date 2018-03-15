package br.com.emmanuelneri;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HikariConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
       final HikariConfig hikariConfig = new HikariConfig();
       hikariConfig.setJdbcUrl(url);
       hikariConfig.setDriverClassName(driver);
       hikariConfig.setUsername(username);
       hikariConfig.setPassword(password);
       hikariConfig.setMaximumPoolSize(20);

        return new HikariDataSource(hikariConfig);
    }

}
