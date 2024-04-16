package shop.mihalen.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mihalen.model.Role;
import shop.mihalen.servive.RoleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/roles")
public class RoleController {
    private final RoleService  roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getAllRoles(@PathVariable("id") String id){
        Role role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role newRole = roleService.addNewRole(role);
        return ResponseEntity.ok(newRole);
    }
}
