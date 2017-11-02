package br.com.emmanuelneri.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public final class BillSummaryDTO {

    private long itemsQuantity;
    private long totalDuration;
    private BigDecimal totalValue;

    public BillSummaryDTO(long itemsQuantity, long totalDuration, BigDecimal totalValue) {
        this.itemsQuantity = itemsQuantity;
        this.totalDuration = totalDuration;
        this.totalValue = totalValue;
    }
}
