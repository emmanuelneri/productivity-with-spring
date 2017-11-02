package br.com.emmanuelneri.service;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.dto.BillResumeDTO;
import br.com.emmanuelneri.dto.BillSummaryDTO;
import br.com.emmanuelneri.dto.NumberUseDTO;
import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.repository.BillItemRepository;
import br.com.emmanuelneri.repository.BillRepository;
import br.com.emmanuelneri.to.BillSearchTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
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

    public Page<BillDTO> search(BillSearchTO searchTO) {
        return billRepository.findAll(searchTO);
    }

    public BillResumeDTO findResume(Long billId) {

        final CompletableFuture<Bill> billFuture = CompletableFuture.supplyAsync(() -> billRepository.findOne(billId));
        final CompletableFuture<BillSummaryDTO> summaryDTOCompletableFuture = CompletableFuture.supplyAsync(() -> billItemRepository.summarizeByBillId(billId));
        final CompletableFuture<List<NumberUseDTO>> most10numbersUseFuture = CompletableFuture.supplyAsync(() -> billItemRepository.numberGreaterUse(billId, new PageRequest(0, 10)));

        try {
           final Bill bill = billFuture.get();
           final BillSummaryDTO summary = summaryDTOCompletableFuture.get();
           final List<NumberUseDTO> most10numbersUse = most10numbersUseFuture.get();

            return new BillResumeDTO(bill.getIdentifier(), bill.getCustomer().getName(), summary, most10numbersUse);
        } catch (InterruptedException | ExecutionException e) {
            log.error("findResume process error" + e);
            throw new RuntimeException();
        }
    }
}
