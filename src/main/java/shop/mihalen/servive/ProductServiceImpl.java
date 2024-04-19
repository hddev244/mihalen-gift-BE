package shop.mihalen.servive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mihalen.dto.CategoryDTO;
import shop.mihalen.dto.ImagesDTO;
import shop.mihalen.dto.ProductDTO;
import shop.mihalen.entity.CategoryEntity;
import shop.mihalen.entity.ImageModel;
import shop.mihalen.entity.ProductEntity;
import shop.mihalen.repository.ImageRepository;
import shop.mihalen.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

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

    @Override
    public ResponseEntity<?> addNewProduct(String productString, MultipartFile[] files) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductEntity product = mapper.readValue(productString, ProductEntity.class);
            Set<ImageModel> images = uploadImage(files);

            imageRepository.saveAll(images);

            product.setImages(images);

            product.setThumbnail(images.iterator().next());

            return addNewProduct(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Update a product
     * 
     * @param product
     * @return ResponseEntity<?>
     */
    @Override
    public ResponseEntity<?> update(ProductEntity product) {

        Map<String, Object> response = new HashMap<>();
        Optional<ProductEntity> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isEmpty()) {
            response.put("message", "Product not found");
            return ResponseEntity.badRequest().body(response);
        }
        ProductEntity existingProduct = optionalProduct.get();
        try {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            if (product.getCategory() != null) {
                existingProduct.setCategory(product.getCategory());
            }
            if (product.getThumbnail() == null && existingProduct.getImages().size() > 0) {
                existingProduct.setThumbnail(existingProduct.getImages().iterator().next());
            }

            productRepository.save(existingProduct);

            response.put("message", "Product updated successfully");
            response.put("data", product);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        List<ProductEntity> products = productRepository.findAll();
        System.out.println("Products: " + products.size());
        response.put("data", "Products found");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> findPages(int index, int size) {
        Pageable pageable = PageRequest.of(index, size);
        Page<ProductEntity> products = productRepository.findAll(pageable);

        Page<ProductDTO> productsDTO = products.map(product -> {
            return copyProductToProductDTO(product);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("data", productsDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Map<String, Object> response = new HashMap<>();
        if (id == null) {
            response.put("message", "Product id is required");
            return ResponseEntity.badRequest().body(response);
        }
        if (productRepository.findById(id).isEmpty()) {
            response.put("message", "Product not found");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            productRepository.deleteById(id);
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Map<String, Object> response = new HashMap<>();

        ProductEntity productExisting = productRepository.findById(id).orElse(null);

        if (productExisting == null) {
            response.put("message", "Product not found");
            return ResponseEntity.badRequest().body(response);
        }

        ProductDTO productDTO = copyProductToProductDTO(productExisting);

        response.put("data", productDTO);
        response.put("message", "Product found");

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> removeImage(Long id, Long imageId) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
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
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(index, size);

        Page<ProductEntity> products = productRepository.findByCategory(id, pageable);

        if (products.isEmpty()) {
            response.put("message", "No products found");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("data", products);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateImages(String productId, MultipartFile[] files) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long pId = Long.parseLong(productId);
            Optional<ProductEntity> optionalProduct = productRepository.findById(pId);

            if (optionalProduct.isEmpty()) {
                response.put("message", "Product not found");
                return ResponseEntity.badRequest().body(response);
            }

            ProductEntity product = optionalProduct.get();
            Set<ImageModel> images = uploadImage(files);
            product.getImages().addAll(images);

            productRepository.save(product);
            ProductDTO productDTO = copyProductToProductDTO(product);

            response.put("data", productDTO);
            response.put("message", "Images updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error: " + e.getMessage());
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Set<ImageModel> uploadImage(MultipartFile[] files) throws Exception {
        Set<ImageModel> images = new HashSet<>();
        if (files == null) {
            return images;
        }
        for (MultipartFile file : files) {
            ImageModel image = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            images.add(image);
        }
        return images;
    }

    private ProductDTO copyProductToProductDTO(ProductEntity product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(new CategoryDTO(product.getCategory().getId(), product.getCategory().getName()));
        productDTO.setCreateDate(product.getCreateDate());
        productDTO.setModifiDate(product.getModifiDate());

        Set<ImagesDTO> imagesDTO = productRepository.findImagesDTOByProductID(product.getId());
        productDTO.setImages(imagesDTO);
        ImagesDTO thumbnail = imagesDTO.iterator().next();
        productDTO.setThumbnail(thumbnail);

        return productDTO;
    }

}
