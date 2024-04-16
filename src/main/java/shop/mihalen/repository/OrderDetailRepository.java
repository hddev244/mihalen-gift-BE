package shop.mihalen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import shop.mihalen.entity.Order;
import shop.mihalen.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

    @Query("SELECT od FROM OrderDetail od WHERE od.order = ?1")
    List<OrderDetail> findByOrder(Order order);
    
}
