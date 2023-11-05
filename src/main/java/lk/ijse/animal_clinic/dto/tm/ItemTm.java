package lk.ijse.animal_clinic.dto.tm;

/*
    @author DanujaV
    @created 10/31/23 - 10:02 AM   
*/

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTm {
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
    private Button btn;
}
