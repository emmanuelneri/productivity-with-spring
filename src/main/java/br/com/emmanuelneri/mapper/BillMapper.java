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

    public static BillDTO toDTO(Bill bill) {
        final BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        dto.setIdentifier(bill.getIdentifier());
        dto.setYearMonth(bill.getYearMonth());
        dto.setCarrier(CarrierMapper.toDTO(bill.getCarrier()));
        dto.setCustomer(CustomerMapper.toDTO(bill.getCustomer()));
        dto.setItems(BillItemMapper.toDTO(bill.getItems()));
        dto.setTotal(bill.getTotal());
        return dto;
    }

}
