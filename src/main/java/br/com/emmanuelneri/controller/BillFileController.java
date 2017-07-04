package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.BillFile;
import br.com.emmanuelneri.service.BillFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/files/bills")
public class BillFileController {

    @Autowired
    private BillFileService billFileService;

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody String content) {
        billFileService.save(content);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BillFile> findAll() {
        return billFileService.findAll();
    }
}
