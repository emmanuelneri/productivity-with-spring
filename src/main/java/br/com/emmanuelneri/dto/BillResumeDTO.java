package br.com.emmanuelneri.dto;

import lombok.Getter;

import java.util.List;

@Getter
public final class BillResumeDTO {

    private String identifier;
    private String customerName;
    private BillSummaryDTO summary;
    private List<NumberUseDTO> most10NumbersUse;

    public BillResumeDTO(String identifier, String customerName, BillSummaryDTO summary, List<NumberUseDTO> most10NumbersUse) {
        this.identifier = identifier;
        this.customerName = customerName;
        this.summary = summary;
        this.most10NumbersUse = most10NumbersUse;
    }
}
