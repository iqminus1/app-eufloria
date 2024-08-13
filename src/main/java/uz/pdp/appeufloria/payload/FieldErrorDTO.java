package uz.pdp.appeufloria.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldErrorDTO implements Serializable {
    private String field;
    private String errorMessage;
}
