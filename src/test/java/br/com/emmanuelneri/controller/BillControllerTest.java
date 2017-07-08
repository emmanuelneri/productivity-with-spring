package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.mapper.BillMapper;
import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.service.BillService;
import br.com.emmanuelneri.test.AbstractWebTest;
import br.com.emmanuelneri.test.data.BillDataMock;
import br.com.emmanuelneri.to.BillSearchTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.time.YearMonth;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BillControllerTest extends AbstractWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillService billService;

    @Inject
    private ObjectMapper objectMapper;

    @Test
    public void findAll() throws Exception {
        final List<Bill> bills = BillDataMock.getFakeBillList();

        given(billService.findAll()).willReturn(bills);

        mockMvc.perform(get("/bills")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(BillMapper.toDTO(bills))));
    }

    @Test
    public void search() throws Exception {
        final List<Bill> bills = BillDataMock.getFakeBillList();

        final Page<BillDTO> billsPaged = new PageImpl<>(BillMapper.toDTO(bills),
                new PageRequest(1, 10), 2L);

        given(billService.search(any(BillSearchTO.class)))
                .willReturn(billsPaged);

        mockMvc.perform(post("/bills/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new BillSearchTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(billsPaged)));
    }

    @Test
    public void findByUk() throws Exception {
        final Bill bill = BillDataMock.getFakeBill();

        given(billService.findByUk(any(Long.class), any(String.class), any(YearMonth.class)))
                .willReturn(bill);

        mockMvc.perform(get("/bills/byUk/1/123/2017-06")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(BillMapper.toDTO(bill))));
    }

    @Test
    public void findById() throws Exception {
        final Bill bill = BillDataMock.getFakeBill();

        given(billService.findById(1L))
                .willReturn(bill);

        mockMvc.perform(get("/bills/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(BillMapper.toDTO(bill))));
    }

    @Test
    public void save() throws Exception {
        final Bill bill = BillDataMock.getFakeBill();

        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(BillMapper.toDTO(bill)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBill() throws Exception {
        mockMvc.perform(delete("/bills/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
