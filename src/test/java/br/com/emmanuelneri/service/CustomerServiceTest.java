package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.test.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

public class CustomerServiceTest extends AbstractIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void findPaged() {
        final int NUMBER_OF_CUSTOMER_INSERT_BY_FLYWAY = 5;
        final int SIZE_PAGINATION = 3;

        final Page<Customer> customerPaged = customerService.findPaginable(1, SIZE_PAGINATION);
        Assert.assertEquals(NUMBER_OF_CUSTOMER_INSERT_BY_FLYWAY, customerPaged.getTotalElements());
        Assert.assertEquals(2, customerPaged.getTotalPages());
        Assert.assertEquals(2, customerPaged.getContent().size());

        Assert.assertThat(customerPaged.getContent(), contains(
                allOf(hasProperty("name", is("Customer 2"))),
                allOf(hasProperty("name", is("Customer 1")))
        ));
    }
}
