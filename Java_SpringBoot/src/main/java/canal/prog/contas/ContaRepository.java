package canal.prog.contas;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByDataVencimentoBetween(LocalDate startDate, LocalDate endDate);

    List<Conta> findByDataVencimentoBetweenAndDescricaoContaining(LocalDate startDate, LocalDate endDate, String descricao);
}
