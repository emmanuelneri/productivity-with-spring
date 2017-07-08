package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.service.CustomerService;
import br.com.emmanuelneri.to.CustomerSearchTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/search/pagenable", method = RequestMethod.POST)
    public Page<Customer> searchPagenable(@RequestBody CustomerSearchTO searchTO) {
        return customerService.searchPagenable(searchTO);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Customer> search(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "name", required = false) String name){
        return customerService.search(new CustomerSearchTO(id, name));
    }
}
