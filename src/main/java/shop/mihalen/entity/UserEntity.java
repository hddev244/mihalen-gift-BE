package shop.mihalen.entity;

import lombok.Data;

@Data
public class UserEntity {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String extraInfo;
}
