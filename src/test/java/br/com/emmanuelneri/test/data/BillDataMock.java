package br.com.emmanuelneri.test.data;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.model.BillItem;
import br.com.emmanuelneri.model.Carrier;
import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.model.ItemType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BillDataMock {

    private static final Carrier carrier = Carrier.builder().id(1L).name("Carrier").build();
    private static final Customer customer = Customer.builder().id(1L).name("Customer").build();

    public static Bill getFakeBill() {
        final List<BillItem> itemsBill123 = new ArrayList<>();
        final Bill bill123 = Bill.builder()
                .carrier(carrier)
                .customer(customer)
                .identifier("123")
                .yearMonth(YearMonth.now())
                .items(itemsBill123)
                .total(BigDecimal.valueOf(1.3))
                .build();

        itemsBill123.add(BillItem.builder()
                .bill(bill123)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898484")
                .destinationNumber("4499898400")
                .duration(100L)
                .description("Local call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(0.3))
                .build());

        itemsBill123.add(BillItem.builder()
                .bill(bill123)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898484")
                .destinationNumber("4499889400")
                .duration(250L)
                .description("Another carrier call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(1))
                .build());

        return bill123;
    }

    public static List<Bill> getFakeBillList() {
        final Bill bill123 = getFakeBill();

        final List<BillItem> items456 = new ArrayList<>();
        final Bill bill456 = Bill.builder()
                .carrier(carrier)
                .customer(customer)
                .identifier("456")
                .yearMonth(YearMonth.now().plusMonths(1))
                .items(items456)
                .total(BigDecimal.valueOf(1.3))
                .build();

        items456.add(BillItem.builder()
                .bill(bill456)
                .dataTime(LocalDateTime.now())
                .originNumber("4499898484")
                .destinationNumber("4499088299")
                .duration(400L)
                .description("Local call")
                .type(ItemType.CALL)
                .value(BigDecimal.valueOf(10))
                .build());

        return Arrays.asList(bill123, bill456);
    }

}
