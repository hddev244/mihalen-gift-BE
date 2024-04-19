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
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    JwtUtils jwtUtils;

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request
            ,@RequestHeader("authorization") String token
            ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        accountService.changePassword(authentication,request);
    return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<Account> updateProfile(@RequestBody Account account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
    return ResponseEntity.ok(account);
    }

}
