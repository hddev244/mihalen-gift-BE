package shop.mihalen.model;

import java.util.ArrayList;
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
public class Account {
    public Account(
            Long id,
            String username,
            String password,
            String fullname,
            String email,
            String photo,
            String address,
            String phoneNumber,
            boolean activated,
            boolean locked,
            List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.photo = photo;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.activated = activated;
        this.locked = locked;
        this.roles = roles;
    }

    private Long id;
    private String username;
    @Nonnull
    @JsonIgnore
    private String password;
    private String fullname;
    private String email;
    private String photo;
    private String address;
    private String phoneNumber;
    private boolean activated;
    private boolean locked;
    private List<Role> roles = new ArrayList();
}
