package shop.mihalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mihalen.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
    
}
