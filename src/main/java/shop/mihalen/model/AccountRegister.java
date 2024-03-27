package shop.mihalen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import shop.mihalen.entity.Role;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
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
    private List<Role> roles = new ArrayList();
}
