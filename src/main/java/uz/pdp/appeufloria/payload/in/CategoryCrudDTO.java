package uz.pdp.appeufloria.payload.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appeufloria.entity.Category;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCrudDTO implements Serializable {
    private String name;
    private Integer parentCategoryId;
}