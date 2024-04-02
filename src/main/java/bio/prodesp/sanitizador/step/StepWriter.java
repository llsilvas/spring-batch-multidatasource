package bio.prodesp.sanitizador.step;

import bio.prodesp.sanitizador.domain.coleta.principal.ColetaPrincipal;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StepWriter {

    @Bean
    public JpaItemWriter<ColetaPrincipal> jpaItemWriter(@Qualifier("coletaPrincipalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<ColetaPrincipal> itemWriter = new JpaItemWriter<>() {
            @Override
            public void write(Chunk<? extends ColetaPrincipal> items) {
                log.debug("[WRITER] - {}", items);
                super.write(items);
            }
        };
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }
}
