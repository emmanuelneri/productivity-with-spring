package br.com.emmanuelneri.model;

import br.com.emmanuelneri.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "bill_uk", columnNames = {"customer_id", "identifier", "year_month"}))
@Getter  @Setter
@Builder
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bill_id_seq")
    @SequenceGenerator(name = "bill_id_seq", sequenceName = "bill_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "carrier_id")
    private Carrier carrier;

    @NotEmpty
    @Size(max = 50)
    private String identifier;

    @NotNull
    @Column(name = "year_month")
    private YearMonth yearMonth;

    @JsonIgnore
    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
    private List<BillItem> items = new ArrayList<>();

    @NotNull
    private BigDecimal total = BigDecimal.ZERO;

    public Bill() {
    }

    public void prepareBillInItems() {
        if(items == null) {
            throw new BusinessException("Items are required in the bill");
        }
        items.forEach(billItem -> billItem.setBill(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;
        Bill bill = (Bill) o;
        return Objects.equals(customer, bill.customer) &&
                Objects.equals(carrier, bill.carrier) &&
                Objects.equals(identifier, bill.identifier) &&
                Objects.equals(yearMonth, bill.yearMonth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, carrier, identifier, yearMonth);
    }
}
