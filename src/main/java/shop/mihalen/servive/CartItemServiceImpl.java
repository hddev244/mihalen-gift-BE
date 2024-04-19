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
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import shop.mihalen.dto.CartItemDTO;
import shop.mihalen.dto.CategoryDTO;
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
import shop.mihalen.utils.AuthUtil;
import shop.mihalen.utils.DtoUtils;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DtoUtils dtoUtils;
    @Autowired
    private AuthUtil authUtil;

    @Override
    public ResponseEntity<?> addToCart(Long productId, Integer quantity) {
        Map<String, Object> response = new HashMap<>();

        AccountEntity accountExisting = authUtil.getAccountFromRequest(request);

        if (accountExisting == null) {
            response.put("message", "Vui lòng đăng nhập trước khi thêm sản phẩm vào giỏ hàng!");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<ProductEntity> productExisting = productRepository.findById(productId);

        if (!productExisting.isPresent()) {
            response.put("message", "Không tìm thấy sản phẩm");
            return ResponseEntity.badRequest().body(response);
        }

        ProductEntity product = productExisting.get();
        CartItem cartItemExisting = cartItemRepository
                .findByProductAndAccount(product.getId(), accountExisting.getUsername())
                .orElse(null);

        // if product already exists in cart, increase quantity
        if (cartItemExisting != null) {
            cartItemExisting.setQuantity(quantity + cartItemExisting.getQuantity());
            cartItemRepository.save(cartItemExisting);

            response.put("message", "Sản phẩm đã tồn tại trong giỏ hàng! Số lượng đã được cập nhật!");
            response.put("data", cartItemExisting);

            return ResponseEntity.ok(response);
        }

        // if product does not exist in cart, add new product to cart
        CartItem cartItem = new CartItem();

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());

        cartItem.setProduct(productEntity);

        cartItem.setAccount(accountExisting);
        cartItem.setQuantity(quantity);

        cartItem = cartItemRepository.save(cartItem);

        CartItemDTO cartItemDTO = dtoUtils.copyToCartItemDTO(cartItem);

        response.put("message", "Thêm sản phẩm vào giỏ hàng thành công!");
        response.put("data", cartItemDTO);

        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<?> getCartPages(Integer index, Integer size) {
        Map<String, Object> response = new HashMap<>();
        AccountEntity accountExisting = authUtil.getAccountFromRequest(request);
        if (accountExisting == null) {
            response.put("message", "Vui lòng đăng nhập trước khi xem giỏ hàng!");
            return ResponseEntity.badRequest().body(response);
        }

        Pageable pageable = PageRequest.of(index, size);
        Page<CartItem> cartItems = cartItemRepository.findPagesByAccount(accountExisting.getUsername(), pageable);
        if (cartItems == null) {
            response.put("message", "Không tìm thấy sản phẩm trong giỏ hàng!");
            return ResponseEntity.badRequest().body(response);
        }

        Page<CartItemDTO> cartItemsDTO = cartItems.map(cartItem -> {
            return dtoUtils.copyToCartItemDTO(cartItem);
        });

        response.put("data", cartItemsDTO);
        return ResponseEntity.ok(response);
    }
}
