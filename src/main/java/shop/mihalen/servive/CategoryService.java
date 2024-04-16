package shop.mihalen.servive;


import org.springframework.http.ResponseEntity;

import shop.mihalen.entity.CategoryEntity;

public interface CategoryService {

    ResponseEntity<?> create(CategoryEntity categoryEntity);

    ResponseEntity<?> findAll(Integer size, Integer index);

    ResponseEntity<?> findAll();  
}
