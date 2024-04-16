package shop.mihalen.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.mihalen.entity.RoleEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Long id;
    private String username;
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
