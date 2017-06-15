package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Bill> findAll() {
        return billService.findAll();
    }

}
