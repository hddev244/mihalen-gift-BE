package shop.mihalen.servive;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mihalen.entity.Role;
import shop.mihalen.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role findById(String roleId) {
        return repository.findById(roleId).get();
    }
}
