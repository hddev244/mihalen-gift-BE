package shop.mihalen.security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;
    // Tạo JWT gửi cho client khi login success
    public String issue(Long userId, String email,String username, List<String> roles){
        return JWT.create()
        .withSubject(username)
        .withExpiresAt(Instant.now().plus(Duration.of(1,ChronoUnit.DAYS)))
        .withClaim("e", email)
        .withClaim("r", roles)
        .withClaim("i", userId)
        .sign(Algorithm.HMAC256(properties.getSecretKey())); //chứ kí , mã private
        // .sign(Algorithm.HMAC256("secrect")); //chứ kí , mã private
    }
}
