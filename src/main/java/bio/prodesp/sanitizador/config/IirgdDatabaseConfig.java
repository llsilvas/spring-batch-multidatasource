package bio.prodesp.sanitizador.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "bio.prodesp.sanitizador.domain.iirgd",
        entityManagerFactoryRef = "iirgdEntityManagerFactory",
        transactionManagerRef = "iirgdDbTxManager"
)
public class IirgdDatabaseConfig {

    @Bean(name = "iirgdDb")
    @ConfigurationProperties(prefix = "iirgd-db.datasource")
    public DataSource iirgdDb() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.type(HikariDataSource.class);
        return builder.build();
    }

    @Bean(name = "iirgdEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean iirgdEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("iirgdDb") DataSource dataSource) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("spring.jpa.hibernate.ddl-auto", "update");

        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("bio.prodesp.sanitizador.domain.iirgd")
                .persistenceUnit("Iirgd")
                .build();
    }

    @Bean(name = "iirgdDbTxManager")
    public PlatformTransactionManager iirgdDbTxManager(@Qualifier("iirgdEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
