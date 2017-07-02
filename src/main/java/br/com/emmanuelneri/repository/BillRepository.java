package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.time.YearMonth;

public interface BillRepository extends JpaRepository<Bill, Long>, QueryDslPredicateExecutor<Bill> {

    Bill findByCustomerIdAndIdentifierAndYearMonth(Long customerId, String identifier, YearMonth yearMonth);
}
