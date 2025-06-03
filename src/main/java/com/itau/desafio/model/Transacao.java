package com.itau.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class Transacao {
    private Double valor;
    private OffsetDateTime dataHora;
}
