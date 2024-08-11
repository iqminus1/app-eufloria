package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.pdp.appeufloria.entity.templates.AbsIntEntity;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@SQLRestriction("deleted = false")
@SQLDelete(sql = "update category set deleted = true where id = ?")
public class Category extends AbsIntEntity implements Serializable {
    private String name;

    @ManyToOne
    private Category parentCategory;
}
