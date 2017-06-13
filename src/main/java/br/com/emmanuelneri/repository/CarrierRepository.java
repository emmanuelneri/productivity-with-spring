package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.model.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrierRepository extends JpaRepository<Carrier, Long> {
}
