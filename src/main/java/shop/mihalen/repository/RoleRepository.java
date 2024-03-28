package shop.mihalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.mihalen.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,String> {
}
