package shop.mihalen.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import shop.mihalen.model.Account;
import shop.mihalen.security.AccountPrincipal;
import shop.mihalen.security.ChangePasswordRequest;
import shop.mihalen.security.JwtUtils;
import shop.mihalen.servive.AccountService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/api/admin/accounts")
    ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAllAccounts());
    }

    @GetMapping("/api/admin/accounts/pages")
    ResponseEntity<Page<Account>> getPageAccounts(
            HttpServletRequest request,
            @RequestParam("index") Optional<Integer> index,
            @RequestParam("size") Optional<Integer> size) {
        AccountPrincipal accountPrincipal = jwtUtils.getAccountPrincipalFromToken(request);
        return ResponseEntity.ok(
                accountService.findPageAccounts(size.orElse(10), index.orElse(0)));
    }

    @GetMapping("/api/account/{username}")
    Account getAccount(@PathVariable("username") String username) {
        return accountService.findByUsername(username).get();
    }

    @GetMapping("/api/admin/account/{username}")
    Account getAccountA(@PathVariable("username") String username) {
        return accountService.findByUsername(username).get();
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

    @DeleteMapping("/api/admin/account/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id){
        // return ResponseEntity.ok().build();
        return accountService.deleteAccount(id);
    }

    @PutMapping("/api/admin/account/lock/{id}")
    public ResponseEntity<?> lockAccount(@PathVariable("id") Long id){
        // return ResponseEntity.ok().build();
        return accountService.lockAccount(id);
    }
    @PostMapping("/api/admin/account")
    public ResponseEntity<?> createProfile(@RequestBody Account account
            ) {
        System.out.println(account.getEmail());
        return accountService.createAccount(account);
    
    }
    @PutMapping("/api/admin/account/profile/{username}")
    public ResponseEntity<Account> updateProfile(@PathVariable("username") String username,@RequestBody Account account
            ) {
        account = accountService.updateAccount(username,account);
    return ResponseEntity.ok(account);
    }

    @PutMapping("/api/account/profile")
    public ResponseEntity<Account> updateProfile(@RequestBody Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        // account = accountService.updateAccount(username,account);
    return ResponseEntity.ok(account);
    }

}
