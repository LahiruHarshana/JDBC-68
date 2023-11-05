package lk.ijse.animal_clinic.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    private String id;
    private String name;
    private String address;
    private String tel;
}
