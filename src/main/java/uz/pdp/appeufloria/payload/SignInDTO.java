package uz.pdp.appeufloria.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInDTO implements Serializable {
    private String username;
    private String password;
}
