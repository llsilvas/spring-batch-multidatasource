package bio.prodesp.sanitizador.domain.coleta.principal.repository;

import bio.prodesp.sanitizador.domain.coleta.principal.ColetaPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColetaPrincipalRepository extends JpaRepository<ColetaPrincipal, Long> {

    Page<ColetaPrincipal> findAllByCpfIsNull(Pageable pageable);
}
