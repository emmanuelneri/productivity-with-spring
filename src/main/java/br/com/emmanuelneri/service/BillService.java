package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.repository.BillItemRepository;
import br.com.emmanuelneri.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillItemRepository billItemRepository;

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    public Bill findByUk(Long customerId, String identifier, YearMonth yearMonth) {
        return billRepository.findByCustomerIdAndIdentifierAndYearMonth(customerId, identifier, yearMonth);
    }

    public Bill findById(Long id) {
        return billRepository.getOne(id);
    }

    @Transactional
    public void delete(Long id) {
        billItemRepository.deleteByBillItem(id);
        billRepository.delete(id);
    }
}
