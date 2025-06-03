package com.itau.desafio.service;

import com.itau.desafio.dto.RelatorioDTO;
import com.itau.desafio.model.Transacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@Slf4j
public class TransacaoService {

    private List<Transacao> transacaoList = new ArrayList<>();

    public void salvar(List<Transacao> transacaoList) {
        log.info("Salvando {} transações.", transacaoList.size());
        if (isValid(transacaoList)){
            this.transacaoList.addAll(transacaoList);
            log.info("{} transações salvas com sucesso.", this.transacaoList.size());
        }
        else {
            log.error("Falha ao salvar transações: Entrada inválida.");
            throw new IllegalArgumentException();
        }

    }

    private boolean isValid(List<Transacao> transacaoList) {
        return transacaoList.stream()
                .allMatch(
                        t -> isValorValid(t.getValor()) && isDataHoraValid(t.getDataHora())
                );
    }

    private boolean isDataHoraValid(OffsetDateTime dataHora) {
        if (dataHora == null) return false;
        return !dataHora.isAfter(OffsetDateTime.now());
    }

    private boolean isValorValid(Double valor) {
        if (valor == null) return false;
        return valor >= 0;
    }

    public void deletarTodos() {
        log.info("Deletando {} transações.", this.transacaoList.size());
        transacaoList.clear();
        log.info("Transações deletadas com sucesso.");
    }

    public RelatorioDTO relatorio(int segundos) {
        if (segundos < 0) {
            log.error("Falha ao gerar relatório: Entrada inválida.");
            throw new IllegalArgumentException();
        }

        log.info("Gerando relatório.");
        long inicio = System.nanoTime();
        OffsetDateTime limite = OffsetDateTime.now().minusSeconds(segundos);
        DoubleSummaryStatistics estatisticas = transacaoList.stream()
                .filter(t -> t.getDataHora().isAfter(limite))
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();
        long fim = System.nanoTime();
        long duracaoMs = (fim - inicio) / 1_000_000;
        log.info(
                "Relatório gerado com sucesso. Total de transações: {}. Tempo de execução: {}ms",
                estatisticas.getCount(),
                duracaoMs
        );
        return new RelatorioDTO(estatisticas);
    }


}
