package shop.mihalen.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import shop.mihalen.security.JwtUtils;
import shop.mihalen.servive.CategoryService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/category")
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
