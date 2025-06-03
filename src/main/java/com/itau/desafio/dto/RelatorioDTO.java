package com.itau.desafio.dto;

import lombok.Data;

import java.util.DoubleSummaryStatistics;

@Data
public class RelatorioDTO {

    private long count;
    private double sum;
    private double min;
    private double max;
    private double avg;

    public RelatorioDTO(DoubleSummaryStatistics relatorio) {
        this.count = relatorio.getCount();
        this.sum = relatorio.getSum();
        this.min = (relatorio.getCount() == 0) ? 0.0: relatorio.getMin();
        this.max = (relatorio.getCount() == 0) ? 0.0: relatorio.getMax();;
        this.avg = relatorio.getAverage();
    }
}
