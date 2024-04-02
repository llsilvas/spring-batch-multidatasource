package bio.prodesp.sanitizador.domain.iirgd;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Coleta")
@Table(name = "tbColeta")
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String rgSp;
    private String cpf;

}
