package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.dto.BillSummaryDTO;
import br.com.emmanuelneri.dto.NumberUseDTO;
import br.com.emmanuelneri.model.BillItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    @Modifying
    @Query("delete from BillItem where bill.id = :#{#billId}")
    void deleteByBillItem(@Param("billId") Long billId);

    @Query(name = "BillItem.summarizeByBillId")
    BillSummaryDTO summarizeByBillId(@Param("billId") Long billId);

    @Query(name = "BillItem.numberGreaterUse")
    List<NumberUseDTO> numberGreaterUse(@Param("billId") Long billId, Pageable pageable);

}
