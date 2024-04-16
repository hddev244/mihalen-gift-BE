package shop.mihalen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mihalen.model.OrderRequest;
import shop.mihalen.servive.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody OrderRequest orderRequest){
        return orderService.addOrder(orderRequest);
    }
}