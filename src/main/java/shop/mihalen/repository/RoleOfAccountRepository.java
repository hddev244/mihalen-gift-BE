package shop.mihalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import shop.mihalen.entity.RoleOfAccount;

@Repository
public interface RoleOfAccountRepository extends JpaRepository<RoleOfAccount, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM RoleOfAccount o WHERE o.account.username = ?1 AND o.role.id = ?2")
    public void removeRole(String username, String roleId);

    @Query("SELECT o FROM RoleOfAccount o WHERE o.account.username = ?1 AND o.role.id = ?2")
    public RoleOfAccount findByUsernameAndRoleId(String username, String roleId);

}
