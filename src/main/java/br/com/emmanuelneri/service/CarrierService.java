package br.com.emmanuelneri.service;

import br.com.emmanuelneri.model.Carrier;
import br.com.emmanuelneri.repository.CarrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrierService {

    @Autowired
    private CarrierRepository carrierRepository;

    public Carrier save(Carrier carrier) {
        return carrierRepository.save(carrier);
    }

}
