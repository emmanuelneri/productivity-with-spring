package br.com.emmanuelneri.mapper;

import br.com.emmanuelneri.dto.BillItemDTO;
import br.com.emmanuelneri.dto.CarrierDTO;
import br.com.emmanuelneri.exception.BusinessException;
import br.com.emmanuelneri.model.Bill;
import br.com.emmanuelneri.model.BillItem;
import br.com.emmanuelneri.model.Carrier;

import java.util.List;
import java.util.stream.Collectors;

public class BillItemMapper {

    public static List<BillItem> fromDTO(List<BillItemDTO> items, Bill bill) {
        if(items == null) {
            throw new BusinessException("Items are required in the bill");
        }

        return items.stream()
                .map(dto -> fromDTO(dto, bill))
                .collect(Collectors.toList());
    }

    public static BillItem fromDTO(BillItemDTO dto, Bill bill) {
        final BillItem billItem = new BillItem();
        billItem.setId(dto.getId());
        billItem.setBill(bill);
        billItem.setDataTime(dto.getDataTime());
        billItem.setDescription(dto.getDescription());
        billItem.setOriginNumber(dto.getOriginNumber());
        billItem.setDestinationNumber(dto.getDestinationNumber());
        billItem.setDuration(dto.getDuration());
        billItem.setValue(dto.getValue());
        billItem.setType(dto.getType());

        return billItem;
    }
}
