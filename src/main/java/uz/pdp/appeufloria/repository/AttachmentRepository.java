package uz.pdp.appeufloria.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appeufloria.entity.Attachment;
import uz.pdp.appeufloria.exceptions.NotFoundException;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    @Cacheable(value = "attachmentEntity", key = "#id")
    default Attachment getById(Integer id) {
        return findById(id).orElseThrow(() -> NotFoundException.errorById("Attachment", id));
    }

    @CachePut(value = "attachmentEntity", key = "#result.id")
    @Override
    Attachment save(Attachment attachment);

    @CacheEvict(value = "attachmentEntity", key = "#attachment.id")
    @Override
    void delete(Attachment attachment);
}