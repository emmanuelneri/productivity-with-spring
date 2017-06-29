package br.com.emmanuelneri.controller;

import br.com.emmanuelneri.model.Carrier;
import br.com.emmanuelneri.service.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carriers")
public class CarrierController {

    @Autowired
    private CarrierService carrierService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Carrier> findAll() {
        return carrierService.findAll();
    }
}
