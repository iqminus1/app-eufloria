package uz.pdp.appeufloria.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appeufloria.entity.Category;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO implements Serializable {
    private Integer id;
    private String name;
    private Integer parentCategoryId;
}