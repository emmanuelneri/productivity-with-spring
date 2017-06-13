package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
