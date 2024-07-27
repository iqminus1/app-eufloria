package up.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import up.pdp.appeufloria.entity.templates.AbsIntEntity;
import up.pdp.appeufloria.enums.PermissionEnum;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Permission extends AbsIntEntity implements Serializable {
    private String name;

    @Enumerated(value = EnumType.STRING)
    private PermissionEnum permission;
}
