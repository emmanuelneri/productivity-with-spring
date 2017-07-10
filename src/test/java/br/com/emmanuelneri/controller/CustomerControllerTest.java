package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.service.CustomerService;
import br.com.emmanuelneri.test.AbstractWebTest;
import br.com.emmanuelneri.to.CustomerSearchTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Inject
    private ObjectMapper objectMapper;

    @Test
    public void findAll() throws Exception {
        final List<Customer> customers = Arrays.asList(new Customer());

        given(customerService.findAll())
                .willReturn(customers);

        mockMvc.perform(get("/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(customers)));
    }

    @Test
    public void searchWithoutParams() throws Exception {
        final List<Customer> customers = Arrays.asList(new Customer());

        given(customerService.search(any(CustomerSearchTO.class)))
                .willReturn(customers);

        mockMvc.perform(get("/customers/search")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(customers)));
    }

    @Test
    public void searchOneParams() throws Exception {
        final List<Customer> customers = Arrays.asList(new Customer());

        given(customerService.search(any(CustomerSearchTO.class)))
                .willReturn(customers);

        mockMvc.perform(get("/customers/search?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(customers)));
    }

    @Test
    public void searchAllParams() throws Exception {
        final List<Customer> customers = Arrays.asList(new Customer());

        given(customerService.search(any(CustomerSearchTO.class)))
                .willReturn(customers);

        mockMvc.perform(get("/customers/search")
                .param("id", "1")
                .param("name", "Customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(customers)));
    }

}
