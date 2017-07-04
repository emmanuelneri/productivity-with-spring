package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.model.BillItem;
import br.com.emmanuelneri.model.Carrier;
import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.model.ItemType;
import br.com.emmanuelneri.test.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

public class BillServiceTest extends AbstractIntegrationTest {

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
    }

    @Test
    public void saveABillWithTwoCalls() {
        final List<BillItem> items = new ArrayList<>();
        final Bill bill = Bill.builder()
                .carrier(carrier)
                .customer(customer)
                .identifier("123")
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

        final Bill savedBill = billService.save(bill);
        Assert.assertNotNull(savedBill.getId());
        Assert.assertEquals(YearMonth.now(), savedBill.getYearMonth());
        Assert.assertEquals(BigDecimal.valueOf(1.3), savedBill.getTotal());

        Assert.assertEquals(2, savedBill.getItems().size());
        Assert.assertThat(savedBill.getItems(), contains(
                allOf(
                    hasProperty("bill", is(bill)),
                    hasProperty("description", is("Local call"))),
                allOf(
                    hasProperty("bill", is(bill)),
                    hasProperty("description", is("Another carrier call")))
                ));
    }

    @Test
    public void updatingABillWithOneMoreCall() {
        final List<BillItem> items = new ArrayList<>();

        final Bill bill = Bill.builder()
                .carrier(carrier)
                .customer(customer)
                .identifier("123")
                .yearMonth(YearMonth.now())
                .items(items)
                .total(BigDecimal.valueOf(1.3))
                .build();

        items.add(BillItem.builder()
                .bill(bill)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898639")
                .destinationNumber("4499898400")
                .duration(40L)
                .description("Local call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(0.63))
                .build());

        items.add(BillItem.builder()
                .bill(bill)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898484")
                .destinationNumber("4499999900")
                .duration(40L)
                .description("Another carrier call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(1.45))
                .build());

        final Bill savedBill = billService.save(bill);
        Assert.assertEquals(2, savedBill.getItems().size());

        savedBill.getItems().add(BillItem.builder()
                .bill(savedBill)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898484")
                .destinationNumber("4499999900")
                .duration(100L)
                .description("Roaming Call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(3.98))
                .build());

        final Bill updatedBill = billService.save(savedBill);
        Assert.assertEquals(3, updatedBill.getItems().size());
        Assert.assertThat(updatedBill.getItems(), contains(
                allOf(
                        hasProperty("bill", is(bill)),
                        hasProperty("description", is("Local call"))),
                allOf(
                        hasProperty("bill", is(bill)),
                        hasProperty("description", is("Another carrier call"))),
                allOf(
                        hasProperty("bill", is(bill)),
                        hasProperty("description", is("Roaming Call")))
        ));
    }

    @Test
    public void findByUk() {
        final List<BillItem> itemsCurrentMonth = new ArrayList<>();
        final Bill billCurrentMonth = Bill.builder()
                .carrier(carrier)
                .customer(customer)
                .identifier("123")
                .yearMonth(YearMonth.now())
                .items(itemsCurrentMonth)
                .total(BigDecimal.valueOf(1))
                .build();

        itemsCurrentMonth.add(BillItem.builder()
                .bill(billCurrentMonth)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898484")
                .destinationNumber("4499898400")
                .duration(100L)
                .description("Local call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(1))
                .build());


        billService.save(billCurrentMonth);

        final List<BillItem> itemsLastMonth = new ArrayList<>();

        final Bill billLastMonth = Bill.builder()
                .carrier(carrier)
                .customer(customer)
                .identifier("123")
                .yearMonth(YearMonth.now().minusMonths(1))
                .items(itemsLastMonth)
                .total(BigDecimal.valueOf(256))
                .build();

        itemsLastMonth.add(BillItem.builder()
                .bill(billCurrentMonth)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898484")
                .destinationNumber("4499898400")
                .duration(800L)
                .description("Local call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(256))
                .build());

        billService.save(billLastMonth);

        final Bill bill = billService.findByUk(customer.getId(), "123", YearMonth.now());
        Assert.assertEquals(YearMonth.now(), bill.getYearMonth());

    }

    @Test(expected = JpaObjectRetrievalFailureException.class)
    public void delete() {

        final Bill bill = Bill.builder()
                .carrier(carrier)
                .customer(customer)
                .identifier("123")
                .yearMonth(YearMonth.now())
                .total(BigDecimal.valueOf(1.3))
                .build();

        bill.setItems(Collections.singletonList(
                BillItem.builder()
                .bill(bill)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898484")
                .destinationNumber("4499898400")
                .duration(100L)
                .description("Local call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(0.3))
                .build()));

        billService.save(bill);

        final Long billId = bill.getId();
        billService.delete(billId);
        billService.findById(billId);
    }

}
