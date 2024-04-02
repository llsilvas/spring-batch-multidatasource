package bio.prodesp.sanitizador.step;

import bio.prodesp.sanitizador.domain.coleta.principal.ColetaPrincipal;
import bio.prodesp.sanitizador.domain.iirgd.Coleta;
import bio.prodesp.sanitizador.domain.iirgd.ColetaIirgdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class StepProcessor implements ItemProcessor<ColetaPrincipal, ColetaPrincipal> {

    private final ColetaIirgdRepository coletaIirgdRepository;


    @Override
    public ColetaPrincipal process(ColetaPrincipal item) {
        Coleta coleta = coletaIirgdRepository.findDistinctFirstByRgSp(item.getRgSp());
        return mapColeta(item, coleta);
    }

    private ColetaPrincipal mapColeta(ColetaPrincipal item, Coleta coleta) {

        return new ColetaPrincipal(item.getIdColeta(), item.getRgSp(), coleta.getCpf(), item.getFinalidade());
    }
}
