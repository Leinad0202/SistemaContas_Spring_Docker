package canal.prog.contas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    public ContaDTO criar(ContaDTO contaDTO) {
        Conta conta = new Conta();
        conta.setDataVencimento(contaDTO.getDataVencimento());
        conta.setDataPagamento(contaDTO.getDataPagamento());
        conta.setValor(contaDTO.getValor());
        conta.setDescricao(contaDTO.getDescricao());
        conta.setSituacao(contaDTO.getSituacao());
        repository.save(conta);
        contaDTO.setId(conta.getId());
        return contaDTO;
    }

    public ContaDTO atualizar(ContaDTO contaDTO, Long contaId) {
        Conta conta = repository.findById(contaId).orElseThrow();
        conta.setDataVencimento(contaDTO.getDataVencimento());
        conta.setDataPagamento(contaDTO.getDataPagamento());
        conta.setValor(contaDTO.getValor());
        conta.setDescricao(contaDTO.getDescricao());
        conta.setSituacao(contaDTO.getSituacao());
        repository.save(conta);
        return contaDTO;
    }

    public ContaDTO alterarSituacao(Long contaId, String situacao) {
        Conta conta = repository.findById(contaId).orElseThrow();
        conta.setSituacao(situacao);
        repository.save(conta);
        return converter(conta);
    }

    public List<ContaDTO> getAll() {
        return repository.findAll().stream().map(this::converter).collect(Collectors.toList());
    }

    public List<ContaDTO> buscarPorFiltro(LocalDate startDate, LocalDate endDate, String descricao) {
        List<Conta> contas = descricao == null ?
            repository.findByDataVencimentoBetween(startDate, endDate) :
            repository.findByDataVencimentoBetweenAndDescricaoContaining(startDate, endDate, descricao);
        return contas.stream().map(this::converter).collect(Collectors.toList());
    }

    public ContaDTO buscarPorId(Long contaId) {
        Conta conta = repository.findById(contaId).orElseThrow();
        return converter(conta);
    }

    public BigDecimal obterValorTotalPagoPorPeriodo(LocalDate startDate, LocalDate endDate) {
        return repository.findByDataVencimentoBetween(startDate, endDate).stream()
                .filter(conta -> "PAGO".equals(conta.getSituacao()))
                .map(Conta::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String delete(Long contaId) {
        repository.deleteById(contaId);
        return "DELETED";
    }

    private ContaDTO converter(Conta conta) {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(conta.getId());
        contaDTO.setDataVencimento(conta.getDataVencimento());
        contaDTO.setDataPagamento(conta.getDataPagamento());
        contaDTO.setValor(conta.getValor());
        contaDTO.setDescricao(conta.getDescricao());
        contaDTO.setSituacao(conta.getSituacao());
        return contaDTO;
    }
}
