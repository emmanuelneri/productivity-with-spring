package br.com.emmanuelneri.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public final class BillDTO {

    private Long id;
    private CustomerDTO customer;
    private CarrierDTO carrier;
    private String identifier;
    private BigDecimal total = BigDecimal.ZERO;
    private List<BillItemDTO> items = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth yearMonth;

}
