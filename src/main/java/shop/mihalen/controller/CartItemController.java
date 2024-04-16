package shop.mihalen.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.mihalen.servive.CartItemService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
        @RequestParam("productId") Long productId
        , @RequestParam("quantity") Integer quantity){
    return  cartItemService.addToCart(productId,quantity);
    }
    @GetMapping("/pages")
    public ResponseEntity<?> getCartPages(@RequestParam("index") Optional<Integer> index,@RequestParam("size") Optional<Integer> size){
        return cartItemService.getCartPages(index.orElse(0), size.orElse(5));
    }
}
