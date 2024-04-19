package shop.mihalen.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mihalen.model.RoleAccountRequest;
import shop.mihalen.servive.RoleOfAccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/account/roles")
public class RoleOfAccountController {
    private final RoleOfAccountService roleOfAccountService;
    @PostMapping
    public ResponseEntity<?> addChangeRole(@RequestBody RoleAccountRequest request) {
        String username = request.getUsername();
        String roleId = request.getRoleId();
        return roleOfAccountService.changeRole(username,roleId);
    }

    @DeleteMapping
    public ResponseEntity<?> removeRole(@RequestBody RoleAccountRequest request) {
        String roleId = request.getRoleId();
        String username = request.getUsername();
        return roleOfAccountService.removeRole(username,roleId);
    }
}
