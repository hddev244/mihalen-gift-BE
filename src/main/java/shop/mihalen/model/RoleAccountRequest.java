package shop.mihalen.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RoleAccountRequest {
    private final String roleId;
    private final String username;
}
