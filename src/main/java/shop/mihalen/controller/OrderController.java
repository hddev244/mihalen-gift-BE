package shop.mihalen.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.mihalen.model.OrderRequest;
import shop.mihalen.servive.OrderService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<?> getMethodName() {
        return orderService.getAllOrdersOfCustomer();
    }

    @GetMapping("/pages")
    public ResponseEntity<?> getPage(
        @RequestParam(value = "index", required = false) Optional<Integer> index,
        @RequestParam(value = "size", required = false) Optional<Integer> size) 
    {
        return orderService.getPageOrdersOfCustomer(index.orElse(0), size.orElse(10));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.addOrder(orderRequest);
    }
}
