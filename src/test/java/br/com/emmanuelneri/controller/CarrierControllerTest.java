package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.Carrier;
import br.com.emmanuelneri.service.CarrierService;
import br.com.emmanuelneri.test.AbstractWebTest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CarrierControllerTest extends AbstractWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarrierService carrierService;

    @Inject
    private ObjectMapper objectMapper;

    @Test
    public void findAll() throws Exception {
        final List<Carrier> carriers = Arrays.asList(new Carrier());

        given(carrierService.findAll())
                .willReturn(carriers);

        mockMvc.perform(get("/carriers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(carriers)));
    }
}
