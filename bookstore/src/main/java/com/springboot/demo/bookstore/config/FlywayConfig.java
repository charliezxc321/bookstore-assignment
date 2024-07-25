package com.springboot.demo.bookstore.config;

import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
public class FlywayConfig {

    private final DataSource dataSource;

    @Bean
    @ConditionalOnProperty(name="flyway.execute", havingValue = "true")
    public Flyway migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .table("flyway_migration")
                .locations("classpath:db.migration")
                .load();

        flyway.migrate();

        return flyway;
    }
}
