package shop.mihalen.servive;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import shop.mihalen.dto.ProductDTO;
import shop.mihalen.entity.CategoryEntity;
import shop.mihalen.entity.ProductEntity;
import shop.mihalen.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public ResponseEntity<?> addNewProduct(ProductEntity product) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(product.getCategory().getId());
        product.setCategory(categoryEntity);
        product.setCategory(categoryEntity);
        product.setGenderSpecific(true);
        product = productRepository.save(product);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product created successfully");
        response.put("data", product);
        return ResponseEntity.ok(response);
    }
    /**
     * Update a product
     * @param product
     * @return ResponseEntity<?>
     */
    @Override 
    public ResponseEntity<?> update(ProductEntity product) {

        Map<String, Object> response = new HashMap<>();
        if(productRepository.findById(product.getId()).isEmpty()){
            response.put("message", "Product not found");
            return ResponseEntity.badRequest().body(response);
        }
        productRepository.save(product);
        response.put("message", "Product updated successfully");
        response.put("data", product);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("data", productRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> findPages(int index, int size) {
        Pageable pageable = PageRequest.of(index, size);
        Page<ProductEntity> products = productRepository.findPagesNoImages(pageable);
        System.out.println("Products: "+products.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("data", products);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> findPagesDTO(int index, int size) {
        Pageable pageable = PageRequest.of(index, size);
        Page<ProductDTO> products = productRepository.findPagesDTO(pageable);

        for(ProductDTO product: products){
            product.setImages(productRepository.findImagesDTOByProductID(product.getId()));
        }
        
        System.out.println("Products: "+products.getContent());
        Map<String, Object> response = new HashMap<>();
        response.put("data", products);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?>  delete(Long id) {
        if(productRepository.findById(id).isEmpty()){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Product not found");
            return ResponseEntity.badRequest().body(response);
        } else {
            productRepository.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok(response);
        }
    }
    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }
    @Override
    public ResponseEntity<?> findByIdResponseEntity(Long id) {
        Map<String, Object> response = new HashMap<>();
       ProductDTO productExisting = productRepository.findByIdDTO(id).orElse(null);
        if(productExisting == null){
            response.put("message", "Product not found");
            return ResponseEntity.badRequest().body(response);
        }
        productExisting.setImages(productRepository.findImagesDTOByProductID(id));
        response.put("data", productExisting);
        response.put("message", "Product found");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<?> removeImage(Long id, Long imageId) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Product not found");
            return ResponseEntity.badRequest().body(response);
        }
        ProductEntity product = optionalProduct.get();
        product.getImages().removeIf(image -> image.getId().equals(imageId));
        // romeve image from database
        imageService.removeImage(imageId);

        productRepository.save(product);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Image removed successfully");
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<?> findByCategory(String id, int index, int size) {
        System.out.println("Category id: "+id);
        System.out.println("Index: "+index);
        System.out.println("Size: "+size);
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(index, size);
         Page<ProductEntity> products = productRepository.findByCategory(id, pageable);

        if(products.isEmpty()){
            response.put("message", "No products found");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("data", products);
        return ResponseEntity.ok(response);
    }  
}
