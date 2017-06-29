package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public Bill save(Bill bill) {
        bill.prepareBillInItems();
        return billRepository.save(bill);
    }

    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    public Bill findByUk(Long customerId, String identifier, YearMonth yearMonth) {
        return billRepository.findByCustomerIdAndIdentifierAndYearMonth(customerId, identifier, yearMonth);
    }
}
