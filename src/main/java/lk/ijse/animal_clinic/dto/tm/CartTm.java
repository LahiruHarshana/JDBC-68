package lk.ijse.animal_clinic.dto.tm;

/*
    @author DanujaV
    @created 10/31/23 - 2:00 PM   
*/

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CartTm {
    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private double tot;
    private Button btn;
}
