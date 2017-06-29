package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
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

    @RequestMapping(value = "/byUk/{customerId}/{identifier}/{yearMonth}", method = RequestMethod.GET)
    public Bill findByUk(@PathVariable("customerId") Long customerId, @PathVariable("identifier") String identifier, @PathVariable("yearMonth") YearMonth yearMonth) {
        return billService.findByUk(customerId, identifier, yearMonth);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody Bill bill) {
        billService.save(bill);
    }

}
