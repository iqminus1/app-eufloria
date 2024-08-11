package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
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
@SQLDelete(sql="update attachment set deleted = true where id = ?")
public class Attachment extends AbsIntEntity implements Serializable {
    private String name;
    private String originalName;
    private String path;
    private String contentType;
    private Long size;
}
