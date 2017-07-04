package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.to.BillSearchTO;
import org.springframework.data.domain.Page;

public interface BillRepositoryCustom {

    Page<BillDTO> findAll(BillSearchTO searchTO);
}
