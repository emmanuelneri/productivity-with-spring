package br.com.emmanuelneri.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bill_item")
@Getter @Setter
@Builder
@AllArgsConstructor
public class BillItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_item_id_seq")
    @SequenceGenerator(name = "bill_item_id_seq", sequenceName = "bill_item_id_seq", allocationSize = 1)
    private Long id;

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dataTime;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotEmpty
    @Size(max = 20)
    @Column(name = "origin_number")
    private String originNumber;

    @Size(max = 20)
    @Column(name = "destination_number")
    private String destinationNumber;

    private Long duration;

    @NotNull
    private BigDecimal value;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemType type;

    protected BillItem() {
    }

}
