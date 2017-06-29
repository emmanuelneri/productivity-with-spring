package br.com.emmanuelneri.dto;

import br.com.emmanuelneri.model.ItemType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillItemDTO {

    private Long id;
    private String description;
    private String originNumber;
    private String destinationNumber;
    private Long duration;
    private BigDecimal value;
    private ItemType type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataTime;
}
