package shop.mihalen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    @Query("SELECT o FROM Order o WHERE o.account = ?1")
    List<Order> findByAccount(AccountEntity account);
    
}
