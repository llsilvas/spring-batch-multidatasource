package bio.prodesp.sanitizador.domain.iirgd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColetaIirgdRepository extends JpaRepository<Coleta, Long> {

    Coleta findDistinctFirstByRgSp(String rgSp);
}
