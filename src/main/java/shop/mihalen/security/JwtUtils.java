package shop.mihalen.security;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    
    public AccountPrincipal getAccountPrincipalFromToken(String token){
        if(token == null) return null;
        return extractTokenFromRequest(token)
                .map(jwtDecoder::decoder)
                .map(jwtToPrincipalConverter::convert).get();
    }

    private Optional<String> extractTokenFromRequest(String token){
        if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    } 

    public AccountPrincipal getAccountPrincipalFromToken(HttpServletRequest request){
        var token = request.getHeader("authorization");
        if(token == null) return null;
        return getAccountPrincipalFromToken(token);       
    } 
}
