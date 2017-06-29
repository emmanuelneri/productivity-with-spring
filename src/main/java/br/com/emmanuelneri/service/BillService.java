package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction;

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

    public Page<Bill> findPaginable(int page, int size) {
        return billRepository.findAll(new PageRequest(page, size, new Sort(Direction.DESC, "id")));
    }

    public Bill findByUk(Long customerId, String identifier, YearMonth yearMonth) {
        return billRepository.findByCustomerIdAndIdentifierAndYearMonth(customerId, identifier, yearMonth);
    }
}
