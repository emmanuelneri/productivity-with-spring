package br.com.emmanuelneri.to;

import br.com.emmanuelneri.model.QCustomer;
import com.google.common.base.Strings;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CustomerSearchTO {

    private Long id;
    private String name;

    private int page;
    private int size = 10;

    public CustomerSearchTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BooleanBuilder toPredicate() {
        final QCustomer qCustomer = QCustomer.customer;
        final BooleanBuilder predicate = new BooleanBuilder();

        if(id != null) {
            predicate.and(qCustomer.id.eq(id));
        }

        if(!Strings.isNullOrEmpty(name)) {
            predicate.and(qCustomer.name.containsIgnoreCase(name));
        }

        return predicate;
    }

}
