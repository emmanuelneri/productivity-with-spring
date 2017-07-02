package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.service.CustomerService;
import br.com.emmanuelneri.to.CustomerSearchTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @RequestMapping(value = "/paged/{page}/{size}", method = RequestMethod.GET)
    public Page<Customer> findAllPaged(@PathVariable("page") int page, @PathVariable("size") int size) {
        return customerService.findPaginable(page, size);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Page<Customer> search(@RequestBody CustomerSearchTO searchTO) {
        return customerService.search(searchTO);
    }
}
