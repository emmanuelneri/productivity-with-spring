package br.com.emmanuelneri.repository;

import br.com.emmanuelneri.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    @Modifying
    @Query("delete from BillItem where bill.id = :#{#billId}")
    void deleteByBillItem(@Param("billId") Long billId);

}
