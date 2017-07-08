package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.Customer;
import br.com.emmanuelneri.repository.CustomerRepository;
import br.com.emmanuelneri.to.CustomerSearchTO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> findPaginable(int page, int size) {
        return customerRepository.findAll(new PageRequest(page, size, new Sort(Sort.Direction.DESC, "name")));
    }

    public Page<Customer> searchPagenable(CustomerSearchTO searchTO) {
        return customerRepository.findAll(searchTO.toPredicate(),
                new PageRequest(searchTO.getPage(), searchTO.getSize(),
                        new Sort(Sort.Direction.DESC, "name")
                                .and(new Sort(Sort.Direction.DESC, "id"))));
    }

    public List<Customer> search(CustomerSearchTO searchTO) {
        return Lists.newArrayList(customerRepository.findAll(searchTO.toPredicate()));
    }
}
