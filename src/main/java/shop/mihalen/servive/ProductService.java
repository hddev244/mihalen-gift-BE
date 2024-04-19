package shop.mihalen.servive;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import shop.mihalen.entity.ProductEntity;

public interface ProductService {
    ResponseEntity<?> addNewProduct(ProductEntity product);
    ResponseEntity<?> update(ProductEntity existingProduct);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findPages(int index, int size);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> removeImage(Long id, Long imageId);
    ResponseEntity<?> findByCategory(String id, int index, int size);
    ResponseEntity<?> updateImages(String productIdS, MultipartFile[] files);
    ResponseEntity<?> addNewProduct(String productString, MultipartFile[] files);
}
