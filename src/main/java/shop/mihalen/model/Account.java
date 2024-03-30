package shop.mihalen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import shop.mihalen.entity.RoleEntity;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Account {
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
    private List<RoleEntity> roles = new ArrayList();
    private Date createDate;
    private Date modifiDate;
}
