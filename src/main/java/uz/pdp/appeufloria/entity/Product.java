package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Product extends AbsIntEntity implements Serializable {
    @ManyToOne
    private Category category;

    private String name;

    private String description;

    private boolean available;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Attachment> attachments;
}
