package shop.mihalen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mihalen.model.RoleAccountRequest;
import shop.mihalen.servive.RoleOfAccountService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/account/roles")
public class RoleOfAccountController {
    private final RoleOfAccountService roleOfAccountService;
    @PostMapping
    public String addRole(@RequestBody RoleAccountRequest request) {
        String username = request.getUsername();
        String roleId = request.getRoleId();
        roleOfAccountService.addRole(username,roleId);
        return "s";
    }

    @DeleteMapping
    public String removeRole(@RequestBody RoleAccountRequest request) {
        Long accountId = request.getAccountId();
        String roleId = request.getRoleId();
        roleOfAccountService.removeRole(accountId,roleId);
        return "s";
    }
    
}
