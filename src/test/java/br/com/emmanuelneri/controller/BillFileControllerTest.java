package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.BillFile;
import br.com.emmanuelneri.service.BillFileService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BillFileControllerTest extends AbstractWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillFileService billFileService;

    @Inject
    private ObjectMapper objectMapper;

    @Test
    public void findAll() throws Exception {
        final List<BillFile> billFiles = Arrays.asList(new BillFile("content"));

        given(billFileService.findAll())
                .willReturn(billFiles);

        mockMvc.perform(get("/files/bills")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(billFiles)));
    }

    @Test
    public void save() throws Exception {
        final BillFile billFile = new BillFile("content");

        mockMvc.perform(post("/files/bills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(billFile))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
