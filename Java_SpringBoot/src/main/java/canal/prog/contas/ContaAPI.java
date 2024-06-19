package canal.prog.contas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/contas", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContaAPI {
    @Autowired
    private ContaService contaService;

    @PostMapping
    @ResponseBody
    public ContaDTO criar(@RequestBody ContaDTO contaDTO) {
        return contaService.criar(contaDTO);
    }

    @PutMapping("/{contaId}")
    @ResponseBody
    public ContaDTO atualizar(@PathVariable("contaId") Long contaId,
                               @RequestBody ContaDTO contaDTO) {
        return contaService.atualizar(contaDTO, contaId);
    }

    @PatchMapping("/{contaId}/situacao")
    @ResponseBody
    public ContaDTO alterarSituacao(@PathVariable("contaId") Long contaId,
                                    @RequestParam("situacao") String situacao) {
        return contaService.alterarSituacao(contaId, situacao);
    }

    @GetMapping
    @ResponseBody
    public List<ContaDTO> getAll() {
        return contaService.getAll();
    }

    @GetMapping("/buscar")
    @ResponseBody
    public List<ContaDTO> buscarPorFiltro(@RequestParam("startDate") LocalDate startDate,
                                          @RequestParam("endDate") LocalDate endDate,
                                          @RequestParam(value = "descricao", required = false) String descricao) {
        return contaService.buscarPorFiltro(startDate, endDate, descricao);
    }

    @GetMapping("/{contaId}")
    @ResponseBody
    public ContaDTO buscarPorId(@PathVariable("contaId") Long contaId) {
        return contaService.buscarPorId(contaId);
    }

    @GetMapping("/valorTotalPago")
    @ResponseBody
    public BigDecimal obterValorTotalPagoPorPeriodo(@RequestParam("startDate") LocalDate startDate,
                                                    @RequestParam("endDate") LocalDate endDate) {
        return contaService.obterValorTotalPagoPorPeriodo(startDate, endDate);
    }

    @DeleteMapping("/{contaId}")
    @ResponseBody
    public String deletar(@PathVariable("contaId") Long contaId) {
        return contaService.delete(contaId);
    }
}
