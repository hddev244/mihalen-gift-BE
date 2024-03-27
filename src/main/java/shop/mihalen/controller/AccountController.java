package shop.mihalen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.var;
import shop.mihalen.entity.AccountEntity;
import shop.mihalen.model.Account;
import shop.mihalen.security.AccountPrincipal;
import shop.mihalen.security.ChangePasswordRequest;
import shop.mihalen.security.JwtUtils;
import shop.mihalen.servive.AccountService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ObjectMapper mapper;

    @GetMapping("/api/admin/accounts")
    List<Account> getAllAccounts() {
        return accountService.findAllAccounts();
    }

    @GetMapping("/api/admin/accounts/pages")
    ResponseEntity<Page<Account>> getPageAccounts(
            @RequestParam("index") Optional<Integer> index,
            @RequestParam("size") Optional<Integer> size) {
        return ResponseEntity.ok(
                accountService.findPageAccounts(size.orElse(10), index.orElse(0)));
    }

    @GetMapping("/api/account/{username}")
    Account getAllAccount(@PathVariable("username") String username) {
        return accountService.findByUsername(username).get();
    }

    @PutMapping("/api/account/profile/{username}")
    public ResponseEntity<Account> updateProfile(@PathVariable("username") String username
            ,@RequestBody Account account) {
        account = accountService.updateAccount(username,account);
    return ResponseEntity.ok(account);
    }

    @PutMapping("/api/account/profile")
    public ResponseEntity<Account> updateProfile(@RequestBody Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        account = accountService.updateAccount(username,account);
    return ResponseEntity.ok(account);
    }

    @PatchMapping("/api/account/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request
            ,@RequestHeader("authorization") String token
            ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        accountService.changePassword(authentication,request);
    return new ResponseEntity<>(HttpStatus.OK);
    }
}
