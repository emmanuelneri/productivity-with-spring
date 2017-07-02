package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.to.BillSearchTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.time.YearMonth;

public interface BillRepositoryCustom {

    Page<BillDTO> findAll(BillSearchTO searchTO);
}
