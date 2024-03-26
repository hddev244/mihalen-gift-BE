package shop.mihalen.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtToPrincipalConverter {

    /**
     * conver jwt decoded to accountPrincipal
     * @param jwt
     * @return
     */
    public AccountPrincipal convert(DecodedJWT jwt){
        return AccountPrincipal.builder()
        .username(jwt.getSubject())
        .email(jwt.getClaim("e").asString())
        .userId(jwt.getClaim("i").asLong())
        .authorities(extractAuthoritiesFromClaim(jwt))
        .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
        var claim = jwt.getClaim("r");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
