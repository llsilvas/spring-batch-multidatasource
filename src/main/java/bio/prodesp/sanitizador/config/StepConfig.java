package bio.prodesp.sanitizador.config;

import bio.prodesp.sanitizador.domain.coleta.principal.ColetaPrincipal;
import bio.prodesp.sanitizador.domain.iirgd.ColetaIirgdRepository;
import bio.prodesp.sanitizador.step.StepProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class StepConfig {


    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final ItemReader<ColetaPrincipal> repositoryItemReader;
    private final JpaItemWriter<ColetaPrincipal> jpaItemWriter;
    private final ColetaIirgdRepository coletaIirgdRepository;

    @Bean
    public Step step(@Qualifier("taskSanitizador") TaskExecutor taskExecutor) {
        return new StepBuilder("step", jobRepository)
                .<ColetaPrincipal, ColetaPrincipal>chunk(10, transactionManager)
                .allowStartIfComplete(true)
                .reader(repositoryItemReader)
                .processor(processor())
                .writer(jpaItemWriter)
                .taskExecutor(taskSanitizador())
                .build();
    }

    private ItemProcessor<ColetaPrincipal, ColetaPrincipal> processor() {
        return new StepProcessor(coletaIirgdRepository);
    }

    @Bean
    @Qualifier("taskSanitizador")
    public TaskExecutor taskSanitizador() {
        return new SimpleAsyncTaskExecutor("sanitizador_batch");
    }
}
