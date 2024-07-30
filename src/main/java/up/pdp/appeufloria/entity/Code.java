package up.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import lombok.*;
import up.pdp.appeufloria.entity.templates.AbsIntEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Code extends AbsIntEntity {
    private String email;
    private String codeString;
    private Integer attempt;
}
