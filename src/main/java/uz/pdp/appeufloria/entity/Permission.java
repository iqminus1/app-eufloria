package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.pdp.appeufloria.entity.templates.AbsIntEntity;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Permission extends AbsIntEntity implements Serializable {
    private String name;
}
