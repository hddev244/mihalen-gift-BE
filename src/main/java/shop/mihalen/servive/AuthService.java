package shop.mihalen.servive;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mihalen.model.LoginResponse;
import shop.mihalen.security.JwtIssuer;
import shop.mihalen.security.AccountPrincipal;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    //sử dụng AuthenticationManager của spring boot để kiểm tra login
    public LoginResponse attempLogin(String username,String password){
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (AccountPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(),principal.getUsername() ,roles);

        return LoginResponse.builder()
        .assessToken(token)
        .build();
    }
}
