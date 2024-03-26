package shop.mihalen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.var;
import shop.mihalen.model.Account;
import shop.mihalen.model.LoginRequest;
import shop.mihalen.model.LoginResponse;
import shop.mihalen.security.JwtIssuer;
import shop.mihalen.servive.AccountService;
import shop.mihalen.servive.AuthService;

@RestController
@RequiredArgsConstructor
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
      // var token = jwtIssuer.issue(1L, "name", "emai", List.of("user"));
      // return LoginResponse.builder().assessToken(token).build();
    }
    @PostMapping("/auth/register")
    public ResponseEntity<Account> saveAccount(@RequestBody @Validated Account account){
      // return new ResponseEntity<>(account,HttpStatus.OK);
      return accountService.saveAccount(account);
    }
}
