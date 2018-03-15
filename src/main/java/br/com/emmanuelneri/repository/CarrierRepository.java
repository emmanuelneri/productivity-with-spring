package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.model.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarrierRepository extends JpaRepository<Carrier, Long> {
    List<Carrier> findAllByName(String name);
}
