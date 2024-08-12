package uz.pdp.appeufloria.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String description;

    private boolean available;

    private List<Integer> attachments;

}
