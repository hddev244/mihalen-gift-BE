package shop.mihalen.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Configuration
@Component
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    private String secretKey;
}
