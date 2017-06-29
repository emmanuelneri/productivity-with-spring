package br.com.emmanuelneri.mapper;

import br.com.emmanuelneri.dto.CarrierDTO;
import br.com.emmanuelneri.model.Carrier;

public class CarrierMapper {

    public static Carrier fromDTO(CarrierDTO dto) {
        return new Carrier(dto.getId(), dto.getName());
    }

}
