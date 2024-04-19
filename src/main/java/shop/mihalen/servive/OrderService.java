package shop.mihalen.servive;

import org.springframework.http.ResponseEntity;

import shop.mihalen.model.OrderRequest;

public interface OrderService {

    ResponseEntity<?> addOrder(OrderRequest orderRequest);

    ResponseEntity<?> getAllOrdersOfCustomer();

    ResponseEntity<?> getPageOrdersOfCustomer(Integer orElse, Integer orElse2);
    
}
