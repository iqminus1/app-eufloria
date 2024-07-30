package uz.pdp.appeufloria.entity.templates;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@MappedSuperclass
public abstract class AbsAuditEntity extends AbsDateEntity {
    @CreatedBy
    @Column(updatable = false)
    private Integer createBy;

    @LastModifiedBy
    private Integer updateBy;
}
