package uz.pdp.appeufloria.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResultDTO<T> {
    private boolean success;
    private T data;
    private String errorMessage;
    private List<FieldErrorDTO> fieldErrors;

    public static <T> ApiResultDTO<T> success(T data) {
        ApiResultDTO<T> apiResultDTO = new ApiResultDTO<>();
        apiResultDTO.setSuccess(true);
        apiResultDTO.setData(data);
        return apiResultDTO;
    }

    public static ApiResultDTO<?> error(String errorMessage) {
        ApiResultDTO<?> apiResultDTO = new ApiResultDTO<>();
        apiResultDTO.setErrorMessage(errorMessage);
        return apiResultDTO;
    }

    public static ApiResultDTO<?> error(MethodArgumentNotValidException exception) {
        List<FieldErrorDTO> fieldErrors = new ArrayList<>();
        for (FieldError fieldError : exception.getFieldErrors()) {
            fieldErrors.add(new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        ApiResultDTO<?> apiResultDTO = new ApiResultDTO<>();
        apiResultDTO.setFieldErrors(fieldErrors);
        return apiResultDTO;
    }
}
