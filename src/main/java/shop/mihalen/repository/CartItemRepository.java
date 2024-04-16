package shop.mihalen.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import shop.mihalen.dto.CartItemDTO;
import shop.mihalen.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Sử dụng khi chưa biết đên fetch.Lazy - Eager , ảnh được lưu dạng byte[] tại database ảnh hưởng
    // tới quá trình lấy dữ liệu. giờ không cần thiết nữa. sửa khi rãnh.
    @Query("SELECT new shop.mihalen.dto.CartItemDTO(c.id"
    + ", c.account.username"
    + ", new shop.mihalen.dto.ProductDTO(" 
    + "  c.product.id" 
    + ", c.product.name" 
    + ", c.product.price" 
    + ", c.product.description" 
    + ", new shop.mihalen.dto.CategoryDTO(c.product.category.id,c.product.category.name)" 
    + ", new shop.mihalen.dto.ImagesDTO(c.product.thumbnail.id,c.product.thumbnail.name)" 
    + ", c.product.createDate" 
    + ", c.product.modifiDate " 
    + ") "
    + ", c.quantity"
    + ") FROM CartItem c WHERE c.account.username = ?2 AND c.product.id = ?1")
    Optional<CartItemDTO> findByProductAndAccount(Long productId, String username);

    // Sử dụng khi chưa biết đên fetch.Lazy - Eager , ảnh được lưu dạng byte[] tại database ảnh hưởng
    // tới quá trình lấy dữ liệu. giờ không cần thiết nữa. sửa khi rãnh.
    @Query("SELECT new shop.mihalen.dto.CartItemDTO(c.id"
            + ", c.account.username"
            + ", new shop.mihalen.dto.ProductDTO(" 
            + "  c.product.id" 
            + ", c.product.name" 
            + ", c.product.price" 
            + ", c.product.description" 
            + ", new shop.mihalen.dto.CategoryDTO(c.product.category.id,c.product.category.name)" 
            + ", new shop.mihalen.dto.ImagesDTO(c.product.thumbnail.id,c.product.thumbnail.name)" 
            + ", c.product.createDate" 
            + ", c.product.modifiDate " 
            + ") "
            + ", c.quantity"
            + ") FROM CartItem c WHERE c.account.username = ?1")
    Page<CartItemDTO> findPagesByAccount(String username, Pageable pageable);

}
