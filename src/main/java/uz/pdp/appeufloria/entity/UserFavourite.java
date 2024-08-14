package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.appeufloria.entity.templates.AbsIntEntity;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class UserFavourite extends AbsIntEntity implements Serializable {
    @ManyToOne
    public User user;

    @ManyToMany
    @ToString.Exclude
    public List<Product> products;

}
