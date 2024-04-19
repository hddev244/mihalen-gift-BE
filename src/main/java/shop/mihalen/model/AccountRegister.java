package shop.mihalen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.mihalen.entity.RoleEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegister {
    @Nonnull
    private String username;
    @Nonnull
    private String password;
    @Nonnull
    private String fullname;
    @Nonnull
    private String email;
    private String address;
    private String phoneNumber;
    private List<RoleEntity> roles = new ArrayList();
}
