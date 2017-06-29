package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.mapper.BillMapper;
import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BillDTO findByUk(@PathVariable("customerId") Long customerId, @PathVariable("identifier") String identifier, @PathVariable("yearMonth") YearMonth yearMonth) {
        return BillMapper.toDTO(billService.findByUk(customerId, identifier, yearMonth));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BillDTO findById(@PathVariable("id") Long id) {
        return BillMapper.toDTO(billService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody BillDTO billDTO) {
        billService.save(BillMapper.fromDTO(billDTO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        billService.delete(id);
    }

}
