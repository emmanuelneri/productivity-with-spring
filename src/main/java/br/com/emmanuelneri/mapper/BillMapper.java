package br.com.emmanuelneri.mapper;

import br.com.emmanuelneri.dto.BillDTO;
import br.com.emmanuelneri.model.Bill;

public class BillMapper {

    public static Bill fromDTO(BillDTO dto) {
        final Bill bill = new Bill();
        bill.setId(dto.getId());
        bill.setIdentifier(dto.getIdentifier());
        bill.setYearMonth(dto.getYearMonth());
        bill.setCarrier(CarrierMapper.fromDTO(dto.getCarrier()));
        bill.setCustomer(CustomerMapper.fromDTO(dto.getCustomer()));
        bill.setItems(BillItemMapper.fromDTO(dto.getItems(), bill));
        bill.setTotal(dto.getTotal());
        return bill;
    }

}
