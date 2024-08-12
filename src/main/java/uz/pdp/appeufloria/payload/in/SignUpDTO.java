package uz.pdp.appeufloria.payload.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpDTO {
    private String email;
    private String username;
    private String password;
    private String acceptedPassword;
}
