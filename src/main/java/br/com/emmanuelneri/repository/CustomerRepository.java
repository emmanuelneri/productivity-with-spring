package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
