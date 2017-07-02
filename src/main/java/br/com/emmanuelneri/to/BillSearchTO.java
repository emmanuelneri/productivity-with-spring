package br.com.emmanuelneri.to;

import br.com.emmanuelneri.model.QBill;
import com.google.common.base.Strings;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.YearMonth;

@Getter @Setter
public class BillSearchTO {

    private String identifier;
    private YearMonth initYearMonth;
    private YearMonth endYearMonth;
    private Long customerId;
    private Long carrierId;

    private int page;
    private int size = 10;

    public BooleanBuilder toPredicate() {
        final QBill qBill = QBill.bill;
        final BooleanBuilder predicate = new BooleanBuilder();

        if(!Strings.isNullOrEmpty(identifier)) {
            predicate.and(qBill.identifier.eq(identifier));
        }

        if(initYearMonth != null) {
            predicate.and(qBill.yearMonth.goe(initYearMonth));
        }

        if(endYearMonth != null) {
            predicate.and(qBill.yearMonth.loe(endYearMonth));
        }

        if(customerId != null) {
            predicate.and(qBill.customer.id.eq(customerId));
        }

        if(carrierId != null) {
            predicate.and(qBill.carrier.id.eq(carrierId));
        }

        return predicate;
    }

    public Pageable getPageable() {
        return new PageRequest(page, size);
    }

}
