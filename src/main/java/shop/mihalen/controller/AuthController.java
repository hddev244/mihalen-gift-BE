package shop.mihalen.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import shop.mihalen.model.Account;
import shop.mihalen.model.AccountRegister;
import shop.mihalen.model.LoginRequest;
import shop.mihalen.model.LoginResponse;
import shop.mihalen.security.JwtIssuer;
import shop.mihalen.servive.AccountService;
import shop.mihalen.servive.AuthService;

@RestController
@RequiredArgsConstructor
// @CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private final AccountService accountService;
    @Autowired
    JwtIssuer jwtIssuer;
    
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
      BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
        System.out.println(pe.encode(request.getPassword()));
       return authService.attempLogin(request.getUsername(),request.getPassword());
    }
    @PostMapping("/auth/register")
    public ResponseEntity<Account> saveAccount(@RequestBody @Validated AccountRegister accountRegister){
      return ResponseEntity.ok(accountService.saveAccount(accountRegister));
    }
}
