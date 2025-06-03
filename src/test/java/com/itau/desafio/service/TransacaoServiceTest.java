package com.itau.desafio.service;

import com.itau.desafio.dto.RelatorioDTO;
import com.itau.desafio.model.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransacaoServiceTest {

    private TransacaoService transacaoService;

    @BeforeEach
    void setup(){
        transacaoService = new TransacaoService();
    }

    @Test
    void deveSalvarTransacaoValida () {
        Transacao t = new Transacao(100.0, OffsetDateTime.now().minusSeconds(10));
        transacaoService.salvar(Collections.singletonList(t));
        RelatorioDTO relatorioDTO = transacaoService.relatorio(60);

        assertEquals(1, relatorioDTO.getCount());
        assertEquals(100.0, relatorioDTO.getSum());
    }

    @Test
    void naoDeveSalvarTransacaoComValorNegativo() {
        Transacao t = new Transacao(-10.0, OffsetDateTime.now().minusSeconds(10));
        assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.salvar(Collections.singletonList(t))
        );
    }

    @Test
    void naoDeveSalvarTransacaoComDataFutura() {
        Transacao t = new Transacao(10.0, OffsetDateTime.now().plusMonths(10));
        assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.salvar(Collections.singletonList(t))
        );
    }

    @Test
    void relatorioDeveIgnorarTransacoesForaDoIntervalo() {
        Transacao t1 = new Transacao(10.0, OffsetDateTime.now().minusSeconds(10));
        Transacao t2 = new Transacao(20.0, OffsetDateTime.now().minusSeconds(90));
        transacaoService.salvar(Arrays.asList(t1,t2));
        RelatorioDTO relatorioDTO = transacaoService.relatorio(60);

        assertEquals(1, relatorioDTO.getCount());
        assertEquals(10.0, relatorioDTO.getSum());
    }

    @Test
    void naoDeveApresentarRelatorioParaIntervaloNegativo() {
        Transacao t = new Transacao(10.0, OffsetDateTime.now());
        transacaoService.salvar(Collections.singletonList(t));

        assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.relatorio(-10)
        );
    }

    @Test
    void deletarTodosDeveLimparLista() {
        Transacao t1 = new Transacao(10.0, OffsetDateTime.now());
        Transacao t2 = new Transacao(20.0, OffsetDateTime.now());
        transacaoService.salvar(Arrays.asList(t1,t2));
        assertEquals(2, transacaoService.relatorio(60).getCount());

        transacaoService.deletarTodos();
        assertEquals(0, transacaoService.relatorio(60).getCount());
    }
}
