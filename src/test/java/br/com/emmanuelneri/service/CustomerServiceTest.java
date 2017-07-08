package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.test.AbstractIntegrationTest;
import br.com.emmanuelneri.to.CustomerSearchTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

public class CustomerServiceTest extends AbstractIntegrationTest {

    private static final int NUMBER_OF_CUSTOMER_INSERT_BY_FLYWAY = 5;
    private static final int SIZE_PAGINATION = 3;

    @Autowired
    private CustomerService customerService;

    @Test
    public void findPaged() {
        final Page<Customer> customerPaged = customerService.findPaginable(1, SIZE_PAGINATION);
        Assert.assertEquals(NUMBER_OF_CUSTOMER_INSERT_BY_FLYWAY, customerPaged.getTotalElements());
        Assert.assertEquals(2, customerPaged.getTotalPages());
        Assert.assertEquals(2, customerPaged.getContent().size());

        Assert.assertThat(customerPaged.getContent(), contains(
                allOf(hasProperty("name", is("Customer 2"))),
                allOf(hasProperty("name", is("Customer 1")))
        ));
    }

    @Test
    public void findPagedByName() {
        final CustomerSearchTO searchByName = new CustomerSearchTO(null, "Customer 1");

        final Page<Customer> customerPaged = customerService.searchPagenable(searchByName);
        Assert.assertEquals(1, customerPaged.getTotalElements());
        Assert.assertEquals(1, customerPaged.getTotalPages());
        Assert.assertEquals(1, customerPaged.getContent().size());

        Assert.assertThat(customerPaged.getContent(), contains(
                allOf(hasProperty("name", is("Customer 1")))
        ));
    }

    @Test
    public void findByIdWithDynamicParams() {
        final CustomerSearchTO searchById = new CustomerSearchTO(1L, null);

        final List<Customer> customers = customerService.search(searchById);
        Assert.assertEquals(1, customers.size());

        Assert.assertThat(customers, contains(
                allOf(hasProperty("name", is("Customer 1")))
        ));
    }
}
