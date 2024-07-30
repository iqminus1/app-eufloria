package uz.pdp.appeufloria.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import uz.pdp.appeufloria.entity.Attachment;
import uz.pdp.appeufloria.payload.AttachmentDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DefaultMapper {
    AttachmentDTO toDTO(Attachment attachment);
}