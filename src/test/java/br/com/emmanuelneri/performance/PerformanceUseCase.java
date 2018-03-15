package br.com.emmanuelneri.performance;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.model.BillItem;
import br.com.emmanuelneri.model.Carrier;
import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.model.ItemType;
import br.com.emmanuelneri.service.BillService;
import br.com.emmanuelneri.service.CarrierService;
import br.com.emmanuelneri.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public abstract class PerformanceUseCase {

    @Autowired
    private CarrierService carrierService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BillService billService;

    private Carrier carrier;
    private Customer customer;

    @Before
    public void init() {
        carrier = carrierService.save(
                Carrier.builder()
                        .name("Carrier Test")
                        .build());

        customer = customerService.save(
                Customer.builder()
                        .name("Customer Test")
                        .build());

        for(int i = 0; i < 10_000; i++) {
            final List<BillItem> items = new ArrayList<>();
            final Bill bill = Bill.builder()
                    .carrier(carrier)
                    .customer(customer)
                    .identifier(String.valueOf(i))
                    .yearMonth(YearMonth.now())
                    .items(items)
                    .total(BigDecimal.valueOf(1.3))
                    .build();

            items.add(BillItem.builder()
                    .bill(bill)
                    .dataTime(LocalDateTime.now())
                    .originNumber("4499898484")
                    .destinationNumber("4499898400")
                    .duration(100L)
                    .description("Local call")
                    .type(ItemType.CALL)
                    .value(BigDecimal.valueOf(0.3))
                    .build());

            items.add(BillItem.builder()
                    .bill(bill)
                    .dataTime(LocalDateTime.now())
                    .originNumber("4499898484")
                    .destinationNumber("4499889400")
                    .duration(250L)
                    .description("Another carrier call")
                    .type(ItemType.CALL)
                    .value(BigDecimal.valueOf(1))
                    .build());

            billService.save(bill);
        }

    }

    protected void runUseCase() {
        long start = System.currentTimeMillis();

        final List<CompletableFuture<?>> futures = new ArrayList<>();

        for(int i = 0; i <= 1_000; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> billService.findAll()));
            futures.add(CompletableFuture.supplyAsync(() -> customerService.findAll()));
            futures.add(CompletableFuture.supplyAsync(() -> carrierService.findAll()));
            futures.add(CompletableFuture.supplyAsync(() -> customerService.findByName("Customer Test")));
            futures.add(CompletableFuture.supplyAsync(() -> carrierService.findByName("Carrier Test")));
        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        log.info(System.currentTimeMillis() - start + " milis");
    }
}
