package shop.mihalen.servive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import shop.mihalen.dto.CartItemDTO;
import shop.mihalen.dto.OrderDTO;
import shop.mihalen.dto.ProductDTO;
import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.CartItem;
import shop.mihalen.entity.Order;
import shop.mihalen.entity.OrderDetail;
import shop.mihalen.model.OrderRequest;
import shop.mihalen.repository.AccountRepository;
import shop.mihalen.repository.CartItemRepository;
import shop.mihalen.repository.OrderRepository;
import shop.mihalen.security.AccountPrincipal;
import shop.mihalen.security.JwtUtils;

@Service 
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CartItemRepository cartItemRepository;



    @Override
    public ResponseEntity<?> addOrder(OrderRequest orderRequest) {
        Map<String, Object> response = new HashMap<>();
        AccountEntity account = getAccountFromRequest(request);
        if (account != null) {
            List<OrderDetail> orderDetails = new ArrayList<>();

            for (Long cartItemId : orderRequest.getCartItemID()) {
                cartItemRepository.findById(cartItemId).ifPresent(cartItem -> {
                    System.out.println(cartItem.getProduct().getName());
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setProduct(cartItem.getProduct());
                    orderDetail.setQuantity(cartItem.getQuantity());
                    orderDetail.setPrice(cartItem.getProduct().getPrice());
                    orderDetails.add(orderDetail);

                    cartItemRepository.delete(cartItem);
                });
            }

            if (orderDetails.size() > 0) {
                Order order = new Order();
                order.setAccount(account);
                order.setOrderDetails(orderDetails);
                order.setName(orderRequest.getName());
                order.setAddress(orderRequest.getAddress());
                order.setPhoneNumber(orderRequest.getPhoneNumber());

                order = orderRepository.save(order);

                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setId(order.getId());
                orderDTO.setName(order.getName());
                orderDTO.setAddress(order.getAddress());
                orderDTO.setPhoneNumber(order.getPhoneNumber());
                orderDTO.setOrderDate(order.getCreateDate());
                
                Set<CartItemDTO> cartItems = new HashSet<>();
                orderDetails.forEach(orderDetail -> {
                    CartItemDTO cartItemDTO = new CartItemDTO();

                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(orderDetail.getProduct().getId());
                    productDTO.setName(orderDetail.getProduct().getName());
                    productDTO.setPrice(orderDetail.getProduct().getPrice());

                    cartItemDTO.setProduct(productDTO);
                    cartItemDTO.setQuantity(orderDetail.getQuantity());
              
                    cartItems.add(cartItemDTO);
                });

                orderDTO.setCartItems(cartItems);
                orderDTO.setTotalPrice(orderDetails.stream().mapToDouble(orderDetail -> orderDetail.getPrice() * orderDetail.getQuantity()).sum());

                response.put("message", "Order added successfully.");
                response.put("data", orderDTO);

                return ResponseEntity.ok(response);
            } else {
                response.put("message", "No items found in cart.");
                return ResponseEntity.badRequest().body(response);
            }

        }

        response.put("message", "Account not found in request. Please login again.");
        return ResponseEntity.badRequest().body(response);
    }

    private AccountEntity getAccountFromRequest(HttpServletRequest rq) {
        AccountPrincipal accountPrincipal = jwtUtils.getAccountPrincipalFromToken(rq);
        if (accountPrincipal != null) {
            String username = accountPrincipal.getUsername();
            return accountRepository.findByUsernameLike(username).orElse(null);
        }
        return null;
    }
}
