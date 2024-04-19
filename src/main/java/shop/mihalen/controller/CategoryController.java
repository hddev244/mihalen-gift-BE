package shop.mihalen.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import shop.mihalen.entity.CategoryEntity;
import shop.mihalen.entity.ImageModel;
import shop.mihalen.security.JwtUtils;
import shop.mihalen.servive.CategoryService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;



@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/pages")
    public ResponseEntity<?> getPage( @RequestParam("index") Optional<Integer> index,
            @RequestParam("size") Optional<Integer> size) {
        return categoryService.findAll(size.orElse(10), index.orElse(0));
    }
    @GetMapping("/all")
    public ResponseEntity<?> getMethodName() {
        return categoryService.findAll();
    }  
}
