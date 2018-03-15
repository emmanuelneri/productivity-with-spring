package br.com.emmanuelneri.performance;

import br.com.emmanuelneri.AppConfig;
import br.com.emmanuelneri.HikariConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppConfig.class, HikariConfiguration.class})
@Slf4j
public class HikariSourceTest extends PerformanceUseCase {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() {
       log.info("DataSource: " + dataSource);
       runUseCase();
    }

}
