package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import uz.pdp.appeufloria.entity.templates.AbsIntEntity;
import uz.pdp.appeufloria.enums.RoleType;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Role extends AbsIntEntity implements Serializable {
    private String name;

    private RoleType type;

    @ManyToMany
    @ToString.Exclude
    private List<Permission> permissions;
}
