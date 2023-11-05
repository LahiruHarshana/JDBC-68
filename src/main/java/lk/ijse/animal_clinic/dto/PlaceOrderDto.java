package lk.ijse.animal_clinic.dto;

import lk.ijse.animal_clinic.dto.tm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceOrderDto {
    private String orderId;
    private String cusId;
    private LocalDate date;
    private List<CartTm> tmList = new ArrayList<>();
}
