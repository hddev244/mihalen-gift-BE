package shop.mihalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mihalen.entity.ImageModel;

public interface ImageRepository extends JpaRepository<ImageModel, Long>{
    
}
