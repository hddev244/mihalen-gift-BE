package shop.mihalen.servive;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import shop.mihalen.entity.ProductEntity;

public interface ProductService {
    ResponseEntity<?> addNewProduct(ProductEntity product);
    ResponseEntity<?> update(ProductEntity existingProduct);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findPages(int index, int size);
    ResponseEntity<?> findPagesDTO(int index, int size);
    ResponseEntity<?> delete(Long id);
    Optional<ProductEntity> findById(Long id);
    ResponseEntity<?> findByIdResponseEntity(Long id);
    ResponseEntity<?> removeImage(Long id, Long imageId);
}
