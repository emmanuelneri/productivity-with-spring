package br.com.emmanuelneri.mapper;

import br.com.emmanuelneri.dto.CarrierDTO;
import br.com.emmanuelneri.model.Carrier;

public final class CarrierMapper {

    private CarrierMapper(){
    }

    public static Carrier fromDTO(CarrierDTO dto) {
        if(dto == null) {
            return null;
        }

        return new Carrier(dto.getId(), dto.getName());
    }

    public static CarrierDTO toDTO(Carrier carrier) {
        return new CarrierDTO(carrier.getId(), carrier.getName());
    }
}
