package uz.pdp.appeufloria.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentDTO implements Serializable {
    Integer id;
    boolean deleted;
    String name;
    String originalName;
    String path;
    String contentType;
    Long size;
}