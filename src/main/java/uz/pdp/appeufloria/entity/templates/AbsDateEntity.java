package uz.pdp.appeufloria.entity.templates;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
@EnableJpaAuditing
public abstract class AbsDateEntity implements Serializable {
    @CreatedDate
    @Column(updatable = false)
    private Timestamp createAt;

    @LastModifiedDate
    private Timestamp updateAt;
}
