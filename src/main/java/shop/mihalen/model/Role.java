package shop.mihalen.model;

import org.hibernate.mapping.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private final String id;
    private final String name;
    private final java.util.List<Account> accounts;
}
