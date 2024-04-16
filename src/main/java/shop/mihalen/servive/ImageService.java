package shop.mihalen.servive;

import org.springframework.http.ResponseEntity;

public interface ImageService {
    ResponseEntity<?> removeImage(Long id);

    ResponseEntity<byte[]> findById(Long id);
}
