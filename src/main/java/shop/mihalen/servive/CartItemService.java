package shop.mihalen.servive;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public interface CartItemService {
    ResponseEntity<?> addToCart(Long productId,Integer quantity);

    ResponseEntity<?> getCartPages(Integer index, Integer size);
}
