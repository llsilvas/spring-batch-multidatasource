package bio.prodesp.sanitizador.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "bio.prodesp.sanitizador.domain.coleta.principal",
        entityManagerFactoryRef = "coletaPrincipalEntityManagerFactory",
        transactionManagerRef = "coletaPrincipalDbTxManager"
)
public class PrincipalDatabaseConfig {

    @Value("${defaultTenant}")
    private Object defaultTenant;

    @Bean(name = "coletaPrincipalDb")
    @ConfigurationProperties(prefix = "bancos")
    public DataSource coletaPrincipalDb() {
        File[] files = Paths.get("bancos").toFile().listFiles();
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        for (File file : files){
            Properties properties = new Properties();
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

            try{
                properties.load(new FileInputStream(file));

                String tenantId = properties.getProperty("name");

                dataSourceBuilder.driverClassName(properties.getProperty("datasource.driver-class-name"));
                dataSourceBuilder.username(properties.getProperty("datasource.username"));
                dataSourceBuilder.password(properties.getProperty("datasource.password"));
                dataSourceBuilder.url(properties.getProperty("datasource.url"));
                dataSourceBuilder.type(HikariDataSource.class);
                resolvedDataSources.put(tenantId, dataSourceBuilder.build());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        AbstractRoutingDataSource dataSource = new MultitenantDataSource();
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);

        dataSource.afterPropertiesSet();
        return dataSource;
    }

    @Primary
    @Bean(name = "coletaPrincipalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean coletaPrincipalEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("coletaPrincipalDb") DataSource dataSource) {

        HashMap<String, Object> properties = new HashMap<>();

        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("bio.prodesp.sanitizador.domain.coleta.principal")
                .persistenceUnit("ColetaPrincipal")
                .build();
    }

    @Primary
    @Bean(name = "coletaPrincipalDbTxManager")
    public PlatformTransactionManager coletaPrincipalDbTxManager(@Qualifier("coletaPrincipalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
