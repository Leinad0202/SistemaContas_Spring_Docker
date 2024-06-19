package canal.prog.contas;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContaDTO {
    private Long id;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private String descricao;
    private String situacao;

    // Getters and Setters
    // ...
}
