package shop.mihalen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mihalen.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
    
    Optional<AccountEntity> findByUsernameLike(String username);
}
