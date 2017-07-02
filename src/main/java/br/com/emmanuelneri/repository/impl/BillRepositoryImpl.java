package br.com.emmanuelneri.repository.impl;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.mapper.BillMapper;
import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.model.QBill;
import br.com.emmanuelneri.repository.BillRepositoryCustom;
import br.com.emmanuelneri.to.BillSearchTO;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BillRepositoryImpl implements BillRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<BillDTO> findAll(BillSearchTO searchTO) {
        final long total = count(searchTO);
        final List<BillDTO> bills = BillMapper.toDTO(find(searchTO));
        return new PageImpl(bills, searchTO.getPageable(), total);
    }

    private List<Bill> find(BillSearchTO searchTO) {
        final JPQLQuery<Bill> jpqlQuery = findAllBaseQuery(searchTO);

        final Pageable pageable = searchTO.getPageable();
        jpqlQuery.offset((long) pageable.getOffset());
        jpqlQuery.limit((long) pageable.getPageSize());

        jpqlQuery.orderBy(QBill.bill.yearMonth.desc(), QBill.bill.id.desc());

        return jpqlQuery.fetch();
    }

    private long count(BillSearchTO searchTO) {
        final JPQLQuery<Bill> jpqlQuery = findAllBaseQuery(searchTO);
        return jpqlQuery
                .distinct()
                .fetchCount();
    }

    private JPQLQuery<Bill> findAllBaseQuery(BillSearchTO searchTO) {
        final QBill qBill = QBill.bill;

        final JPQLQuery jpaQuery = new JPAQuery(entityManager)
                .from(qBill)
                .join(qBill.carrier).fetchJoin()
                .join(qBill.customer).fetchJoin()
                .join(qBill.items).fetchJoin();

        jpaQuery.where(searchTO.toPredicate());

        return jpaQuery;
    }
}
