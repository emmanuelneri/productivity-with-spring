package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.mapper.BillMapper;
import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.service.BillService;
import br.com.emmanuelneri.to.BillSearchTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @RequestMapping(method = RequestMethod.GET)
    public List<BillDTO> findAll() {
        return BillMapper.toDTO(billService.findAll());
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Page<BillDTO> search(@RequestBody BillSearchTO searchTO) {
        final Page<Bill> billPaged = billService.search(searchTO);
        final List<BillDTO> billsDTO = BillMapper.toDTO(billPaged.getContent());
        return new PageImpl<>(billsDTO);
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
