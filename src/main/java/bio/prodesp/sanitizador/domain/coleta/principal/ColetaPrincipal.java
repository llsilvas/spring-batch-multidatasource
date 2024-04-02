package bio.prodesp.sanitizador.domain.coleta.principal;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ColetaPrincipal")
@Table(name = "tbColetaPrincipal")
public class ColetaPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idColeta;
    private String rgSp;
    private String cpf;
    private String finalidade;

}
