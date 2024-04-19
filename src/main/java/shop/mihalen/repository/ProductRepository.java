package shop.mihalen.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shop.mihalen.dto.ImagesDTO;
import shop.mihalen.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

    @Query("SELECT new shop.mihalen.dto.ImagesDTO(i.id,i.name) FROM ProductEntity p JOIN p.images i WHERE p.id = :id")
    Set<ImagesDTO> findImagesDTOByProductID(Long id);
   
    @Query("SELECT p FROM ProductEntity p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    Page<ProductEntity> search(String keyword, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.category.id = :id")
    Page<ProductEntity> findByCategory(String id, Pageable pageable);
}
