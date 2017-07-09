package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.exception.BusinessException;
import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.service.BillService;
import br.com.emmanuelneri.test.AbstractWebTest;
import br.com.emmanuelneri.test.data.ConstraintViolationMock;
import br.com.emmanuelneri.to.ExceptionTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionHandlerTest extends AbstractWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillService billService;

    @Inject
    private ObjectMapper objectMapper;

    @Test
    public void businessExceptionTest() throws Exception {
        given(billService.save(any(Bill.class)))
                .willThrow(new BusinessException("Erro de validação"));

        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new BillDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        Collections.singleton(new ExceptionTO("Erro de validação")))));
    }

    @Test
    public void constraintViolationExceptionTest() throws Exception {
        given(billService.save(any(Bill.class)))
                .willThrow(ConstraintViolationMock.getFakeConstraintViolationException());

        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new BillDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        Collections.singleton(new ExceptionTO("The identifier is required")))));
    }

    @Test
    public void dataIntegrityViolationExceptionTest() throws Exception {
        given(billService.save(any(Bill.class)))
                .willThrow(new DataIntegrityViolationException("This registry already exists in the database."));

        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new BillDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        Collections.singleton(new ExceptionTO("This registry already exists in the database.")))));
    }

    @Test
    public void unexpectedExceptionTest() throws Exception {
        given(billService.save(any(Bill.class)))
                .willThrow(new RuntimeException());

        mockMvc.perform(post("/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new BillDTO()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("The system is unavailable."));
    }
}
