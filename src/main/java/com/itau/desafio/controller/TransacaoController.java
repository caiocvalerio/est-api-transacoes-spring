package com.itau.desafio.controller;

import com.itau.desafio.dto.RelatorioDTO;
import com.itau.desafio.model.Transacao;
import com.itau.desafio.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Transações", description = "Operações relacionadas a transações financeiras")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Operation(summary = "Recebe uma lista de transações")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Transações salvas com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @PostMapping("/transacao")
    public ResponseEntity<Void> receberTransacoes(@RequestBody List<Transacao> transacaoList) {
        transacaoService.salvar(transacaoList);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Limpa todas as transações")
    @ApiResponse(responseCode = "200", description = "Transações removidas com sucesso")
    @DeleteMapping("/transacao")
    public ResponseEntity<Void> limparTransacao() {
        transacaoService.deletarTodos();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Calcula as estatísticas das transações")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    @GetMapping("/estatistica")
    public ResponseEntity<RelatorioDTO> calcularEstatisticao(@RequestParam(defaultValue = "60") int segundos) {
        return ResponseEntity.ok(transacaoService.relatorio(segundos));
    }

}
