package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.appeufloria.entity.templates.AbsIntEntity;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Code extends AbsIntEntity implements Serializable {
    private String email;
    private String codeString;
    private Integer attempt;
}
