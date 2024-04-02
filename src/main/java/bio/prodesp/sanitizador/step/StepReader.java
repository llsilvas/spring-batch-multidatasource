package bio.prodesp.sanitizador.step;

import bio.prodesp.sanitizador.domain.coleta.principal.ColetaPrincipal;
import bio.prodesp.sanitizador.domain.coleta.principal.repository.ColetaPrincipalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StepReader {


    private final ColetaPrincipalRepository coletaPrincipalRepository;

    @Bean
    public RepositoryItemReader<ColetaPrincipal> repositoryItemReader(@Qualifier("coletaPrincipalDb") DataSource dataSource) {

        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("idColeta", Sort.Direction.ASC);

        RepositoryItemReader<ColetaPrincipal> reader = new RepositoryItemReader<>() {
            @Override
            protected ColetaPrincipal doRead() throws Exception {
                return super.doRead();
            }
        };
        reader.setRepository(coletaPrincipalRepository);
        reader.setMethodName("findAllByCpfIsNull");
        reader.setSort(sortMap);
        reader.setPageSize(1000);
        return reader;
    }
}
