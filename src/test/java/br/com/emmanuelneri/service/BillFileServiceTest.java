package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.BillFile;
import br.com.emmanuelneri.test.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

public class BillFileServiceTest extends AbstractIntegrationTest {

    @Autowired
    private BillFileService billFileService;

    @Test
    public void save() {
        final String json = "{\"customer\":{\"id\":1,\"name\":\"Customer 1\"}," +
                "\"carrier\":{\"id\":1,\"name\":\"TIM\"},\"identifier\":\"29302\",\"yearMonth\":\"2017-07\"," +
                "\"items\":[" +
                "{\"dataTime\":\"2017-07-01 15:30:00\",\"description\":\"Ligação fora do plano\",\"originNumber\":\"4499898484\",\"destinationNumber\":\"1199848248\",\"duration\":\"10\",\"value\":\"50\",\"type\":\"SERVICE\"}," +
                "{\"dataTime\":\"2017-07-15 00:00:00\",\"description\":\"Pacote\",\"originNumber\":\"4499898484\",\"value\":\"50\",\"type\":\"SERVICE\"}]," +
                "\"total\":70.00}";


        billFileService.save(json);

        final List<BillFile> billFiles = billFileService.findAll();

        Assert.assertEquals(1, billFiles.size());
        Assert.assertThat(billFiles, contains(
                allOf(hasProperty("id", notNullValue()),
                        hasProperty("date", notNullValue()),
                        hasProperty("content", is(json)))));

        billFiles.forEach(billFile ->  billFileService.delete(billFile.getId()));
    }

}
