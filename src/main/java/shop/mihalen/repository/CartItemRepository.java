package shop.mihalen.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import shop.mihalen.dto.CartItemDTO;
import shop.mihalen.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c WHERE c.product.id = ?1 AND c.account.username = ?2")
    Optional<CartItem> findByProductAndAccount(Long productId, String username);

    @Query("Select c From CartItem c WHERE c.account.username = ?1")
    Page<CartItem> findPagesByAccount(String username, Pageable pageable);

}
