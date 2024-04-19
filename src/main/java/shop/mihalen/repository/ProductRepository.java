package shop.mihalen.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shop.mihalen.dto.ImagesDTO;
import shop.mihalen.dto.ProductDTO;
import shop.mihalen.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    @Query("SELECT new ProductEntity("+
        "p.id, p.name, p.price, p.available, p.genderSpecific, p.description, new CategoryEntity(p.category.id,p.category.name),  p.createDate, p.modifiDate"
                + ") FROM ProductEntity p")
    List<ProductEntity> findAllNoImages();

    @Query("SELECT new ProductEntity(p.id,p.name,p.price,p.available,p.description,new CategoryEntity(p.category.id,p.category.name), p.createDate, p.modifiDate,p.thumbnail) FROM ProductEntity p ")
    Page<ProductEntity> findPagesNoImages(Pageable pageable);

    @Query("SELECT new shop.mihalen.dto.ImagesDTO(i.id,i.name) FROM ProductEntity p JOIN p.images i WHERE p.id = :id")
    Set<ImagesDTO> findImagesDTOByProductID(Long id);
   
    @Query("SELECT new shop.mihalen.dto.ProductDTO("+
                " p.id"+
                 ", p.name"+
                ", p.price"+
                ", p.description"+
                 ", new shop.mihalen.dto.CategoryDTO(p.category.id,p.category.name)"+
                ", new shop.mihalen.dto.ImagesDTO(p.thumbnail.id,p.thumbnail.name)" +
                ", p.createDate"+
                  ", p.modifiDate "+
                ") " +
                " FROM ProductEntity p WHERE p.category.id = :id"
                )
    Page<ProductEntity> findByCategory(String id, Pageable pageable);
}
