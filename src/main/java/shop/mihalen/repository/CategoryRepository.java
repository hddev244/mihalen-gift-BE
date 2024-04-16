package shop.mihalen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shop.mihalen.dto.CategoryDTO;
import shop.mihalen.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,String> {
    @Query("SELECT new shop.mihalen.dto.CategoryDTO(c.id,c.name,new shop.mihalen.dto.ImagesDTO(c.thumbnail.id,c.thumbnail.name)) FROM CategoryEntity c")
    Page<CategoryDTO> findAllDTO(Pageable pageable);

}
