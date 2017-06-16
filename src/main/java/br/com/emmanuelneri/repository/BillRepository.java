package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Bill findByCustomerIdAndIdentifierAndYearMonth(Long customerId, String identifier, YearMonth yearMonth);
}
