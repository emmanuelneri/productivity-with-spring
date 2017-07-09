package br.com.emmanuelneri.mapper;

import br.com.emmanuelneri.dto.CustomerDTO;
import br.com.emmanuelneri.model.Customer;

public final class CustomerMapper {

    private CustomerMapper() {
    }

    public static Customer fromDTO(CustomerDTO dto) {
        if(dto == null) {
            return null;
        }

        return new Customer(dto.getId(), dto.getName());
    }

    public static CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName());
    }
}
