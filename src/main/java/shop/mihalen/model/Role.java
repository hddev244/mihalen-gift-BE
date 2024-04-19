package shop.mihalen.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private final String id;
    private final String name;
    private final List<Account> accounts;
}
