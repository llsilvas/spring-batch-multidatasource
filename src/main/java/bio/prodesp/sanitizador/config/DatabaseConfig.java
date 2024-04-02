package bio.prodesp.sanitizador.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring-batch-db.datasource")
    public DataSource springBatchDb() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.type(HikariDataSource.class);
        return builder.build();
    }
}
