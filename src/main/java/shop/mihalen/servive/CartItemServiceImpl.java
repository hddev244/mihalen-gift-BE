package shop.mihalen.servive;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import shop.mihalen.dto.CartItemDTO;
import shop.mihalen.dto.ImagesDTO;
import shop.mihalen.dto.ProductDTO;
import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.CartItem;
import shop.mihalen.entity.ImageModel;
import shop.mihalen.entity.ProductEntity;
import shop.mihalen.repository.AccountRepository;
import shop.mihalen.repository.CartItemRepository;
import shop.mihalen.repository.ProductRepository;
import shop.mihalen.security.AccountPrincipal;
import shop.mihalen.security.JwtUtils;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    HttpServletRequest request;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<?> addToCart(Long productId, Integer quantity) {
        Map<String, Object> response = new HashMap<>();

        AccountEntity accountExisting = getAccountFromRequest(request);
        if (accountExisting != null) {
            Optional<ProductDTO> productDTO = productRepository.findByIdDTO(productId);
            if (productDTO.isPresent()) {
                CartItemDTO cartItemDTO = new CartItemDTO();
                ProductDTO productExisting = productDTO.get();

                CartItemDTO cartItemExisting = cartItemRepository
                        .findByProductAndAccount(productExisting.getId(), accountExisting.getUsername())
                        .orElse(null);

                // if product already exists in cart, increase quantity
                if (cartItemExisting != null) {
                    cartItemExisting.setQuantity(quantity + cartItemExisting.getQuantity());
                    CartItem cartItem = convertCartItemDTOToCartItem(cartItemExisting);

                    cartItemRepository.save(cartItem);

                    response.put("message", "Sản phẩm đã tồn tại trong giỏ hàng! Số lượng đã được cập nhật!");
                    response.put("data", cartItemExisting);
                    return ResponseEntity.ok(response);
                }
                // if product does not exist in cart, add new product to cart
                CartItem cartItem = new CartItem();

                ProductEntity productEntity = new ProductEntity();
                productEntity.setId(productExisting.getId());

                cartItem.setProduct(productEntity);

                cartItem.setAccount(accountExisting);
                cartItem.setQuantity(quantity);

                cartItem = cartItemRepository.save(cartItem);

                cartItemDTO = setCartItemDTO(cartItem, accountExisting.getUsername());

                response.put("message", "Thêm sản phẩm vào giỏ hàng thành công!");
                response.put("data", cartItemDTO);
                return ResponseEntity.ok(response);

            } else {
                response.put("message", "Không tìm thấy sản phẩm");
            }
        } else {
            response.put("message", "Vui lòng đăng nhập trước khi thêm sản phẩm vào giỏ hàng!");
        }
        return ResponseEntity.badRequest().body(response);
    }

    private CartItemDTO setCartItemDTO(CartItem cartItem, String username) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(cartItem.getProduct().getId());
        productDTO.setName(cartItem.getProduct().getName());
        productDTO.setPrice(cartItem.getProduct().getPrice());
        cartItemDTO.setProduct(productDTO);

        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setUsername(username);
        return cartItemDTO;
    }

    private AccountEntity getAccountFromRequest(HttpServletRequest rq) {
        AccountPrincipal accountPrincipal = jwtUtils.getAccountPrincipalFromToken(rq);
        if (accountPrincipal != null) {
            String username = accountPrincipal.getUsername();
            return accountRepository.findByUsernameLike(username).orElse(null);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getCartPages(Integer index, Integer size) {
        AccountEntity accountExisting = getAccountFromRequest(request);
        if (accountExisting != null) {
            Pageable pageable = PageRequest.of(index, size);
            Page<CartItemDTO> cartItems = cartItemRepository.findPagesByAccount(accountExisting.getUsername(), pageable);
            if (cartItems != null) {
                for (CartItemDTO cartItem : cartItems.getContent()) {
                    Set<ImagesDTO> images = productRepository.findImagesDTOByProductID(cartItem.getProduct().getId());
                    cartItem.getProduct().setImages(images);
                }
                return ResponseEntity.ok(cartItems);
            }
        }
        return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm trong giỏ hàng!");
    }

    private CartItem convertCartItemDTOToCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setQuantity(cartItemDTO.getQuantity());

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(cartItemDTO.getUsername());

        cartItem.setAccount(accountRepository.findByUsernameLike(cartItemDTO.getUsername()).orElse(null));

        ProductEntity product = new ProductEntity();
        product.setId(cartItemDTO.getProduct().getId());
        cartItem.setProduct(product);

        return cartItem;
    }

}
