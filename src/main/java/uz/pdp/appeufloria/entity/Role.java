package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
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
@SQLRestriction("deleted = false")
@SQLDelete(sql="update role set deleted = true where id = ?")
public class Role extends AbsIntEntity implements Serializable {
    private String name;

    private RoleType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Permission> permissions;
}
