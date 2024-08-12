package uz.pdp.appeufloria.payload.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCrudDTO {
    @NotNull
    private Integer categoryId;

    @NotNull
    @NotBlank
    private String name;

    private String description;

    private boolean available;

    private List<Integer> attachmentIds;
}
