package shop.mihalen.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mihalen.entity.ImageModel;
import shop.mihalen.entity.ProductEntity;
import shop.mihalen.servive.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return productService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return productService.findByIdResponseEntity(id);
    }

    @GetMapping("/pages")
    public ResponseEntity<?> findPages(@RequestParam("index") int index, @RequestParam("size") int size){
        return productService.findPagesDTO(index, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> delete(Long id){
        return productService.delete(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewProduct(
            @RequestPart("product") String productString,
            @RequestPart(name="imageFile", required = false) MultipartFile[] files
    ){
        try{
            ObjectMapper mapper = new ObjectMapper();
            ProductEntity product = mapper.readValue(productString, ProductEntity.class);
            Set<ImageModel> images = uploadImage(files);
            
            product.setImages(images);
            product.setThumbnail(images.iterator().next());
            return productService.addNewProduct(product);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(
        @RequestBody ProductEntity productUpdate
    ){
        Map<String, Object> response = new HashMap<>();
        Optional<ProductEntity> optionalProduct = productService.findById(productUpdate.getId());
        if(optionalProduct.isEmpty()){
            response.put("message", "Product not found");
            return ResponseEntity.badRequest().body(response);
        }
        ProductEntity existingProduct = optionalProduct.get();
        try{
            existingProduct.setName(productUpdate.getName());
            existingProduct.setPrice(productUpdate.getPrice());
            existingProduct.setDescription(productUpdate.getDescription());
            if(productUpdate.getCategory() != null){
                existingProduct.setCategory(productUpdate.getCategory());
            }
            if(productUpdate.getThumbnail() == null && existingProduct.getImages().size() > 0){
                existingProduct.setThumbnail(existingProduct.getImages().iterator().next());
            }
            return productService.update(existingProduct);
        }catch(Exception e){
            System.out.println(e.getMessage());
            response.put("message", "Error: "+e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping(value="/removeImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> removeImage(
        @RequestPart("productId") String productIdS
        ,@RequestPart("imageId") String imageIdS){
            try{
                Long productId = Long.parseLong(productIdS);
                Long imageId = Long.parseLong(imageIdS);
                return productService.removeImage(productId, imageId);
            }catch(Exception e){
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Error: "+e.getMessage());
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
    }
    @PutMapping(value="/updateImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImages(
        @RequestPart("productId") String productIdS,
        @RequestPart(name="imageFile", required = false) MultipartFile[] files
    )
    {
        Map<String, Object> response = new HashMap<>();
        try{
            Long productId = Long.parseLong(productIdS);
            Optional<ProductEntity> optionalProduct = productService.findById(productId);
            if(optionalProduct.isEmpty()){
                response.put("message", "Product not found");
                return ResponseEntity.badRequest().body(response);
            }

            ProductEntity product = optionalProduct.get();
            Set<ImageModel> images = uploadImage(files);
            product.getImages().addAll(images);
            return productService.update(product);
        }catch(Exception e){
            response.put("message", "Error: "+e.getMessage());
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(Long id){
        return productService.delete(id);
    }

    public Set<ImageModel> uploadImage(MultipartFile[] files) throws Exception{
        Set<ImageModel> images = new HashSet<>(); 
        if(files == null){
            return images;
        }
        for(MultipartFile file: files){
            ImageModel image = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            images.add(image);
        }
        return images;
    }
}
