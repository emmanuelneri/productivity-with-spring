package br.com.emmanuelneri.mapper;

import br.com.emmanuelneri.dto.CustomerDTO;
import br.com.emmanuelneri.model.Customer;

public class CustomerMapper {

    public static Customer fromDTO(CustomerDTO dto) {
        return new Customer(dto.getId(), dto.getName());
    }

}
