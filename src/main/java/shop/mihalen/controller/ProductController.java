package shop.mihalen.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import shop.mihalen.entity.ProductEntity;
import shop.mihalen.repository.ImageRepository;
import shop.mihalen.servive.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ImageRepository imageRepository;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return productService.findById(id);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findByCategory(@PathVariable String id, @RequestParam("index") Optional<Integer> index, @RequestParam("size") Optional<Integer> size){
        return productService.findByCategory(id, index.orElse(0), size.orElse(10));
    }

    @GetMapping("/pages")
    public ResponseEntity<?> findPages(
        @RequestParam(value="index",required = false) Optional<Integer> index, 
        @RequestParam(value="size",required = false) Optional<Integer> size){
        return productService.findPages(index.orElse(0), size.orElse(10));
    }
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewProduct(
            @RequestPart("product") String productString,
            @RequestPart(name="imageFile", required = false) MultipartFile[] files
    ){
        return productService.addNewProduct(productString, files);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(
        @RequestBody ProductEntity productUpdate
    ){
        return productService.update(productUpdate);
    }
    @GetMapping("/search")
    public ResponseEntity<?> search(
        @RequestParam("keyword") String keyword,
        @RequestParam("index") Optional<Integer> index,
        @RequestParam("size") Optional<Integer> size
    ){
        return productService.search(keyword, index.orElse(0), size.orElse(10));
    }
}
