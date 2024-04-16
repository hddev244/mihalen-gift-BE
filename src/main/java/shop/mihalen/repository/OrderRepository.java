package shop.mihalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mihalen.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
