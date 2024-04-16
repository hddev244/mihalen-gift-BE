package shop.mihalen.servive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import shop.mihalen.dto.CategoryDTO;
import shop.mihalen.entity.CategoryEntity;
import shop.mihalen.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository repository;
    @Override
    public ResponseEntity<?> create(CategoryEntity categoryEntity) {
        Map<String, Object> rp = new HashMap<>();
        repository.save(categoryEntity);
        rp.put("message", "Tạo thành công!");
        rp.put("data",categoryEntity);
        return ResponseEntity.ok().body(rp);
    }
    @Override
    public ResponseEntity<?> findAll(Integer size, Integer index) {
        Map<String, Object> rp = new HashMap<>();

        Pageable pageable = PageRequest.of(index, size);
        Page<CategoryDTO> categoryEntities = repository.findAllDTO(pageable);

        rp.put("data",categoryEntities);
        return ResponseEntity.ok().body(rp);
    }
    @Override
    public ResponseEntity<?> findAll() {
        Map<String, Object> rp = new HashMap<>();

        List<CategoryEntity> categoryEntities = repository.findAll();

        rp.put("data",categoryEntities);
        rp.put("message", "Lấy dữ liệu thành công!");
        return ResponseEntity.ok().body(rp);
    }    
}
