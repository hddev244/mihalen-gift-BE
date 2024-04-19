package shop.mihalen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private UnauthorizedHandler unauthorizedHandler;
    
    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // add filter for http security
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(login -> login.disable())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(unauthorizedHandler))
                .securityMatcher("/**") // cấu hình đường dẫn cần bảo vệ
                .authorizeRequests(registry -> registry
                                .requestMatchers(
                                        "/",
                                        "/api/v1/auth/**"
                                        ).permitAll()
                                .requestMatchers(
                                        "/api/v1/account/**",
                                        "/api/v1/order/**"
                                        )
                                        .hasAnyRole("USER","ADMIN","CUSTOMER")
                                .requestMatchers(
                                        "/api/v1/cart/**"
                                        )
                                        .hasAnyRole("USER","ADMIN")
                                .requestMatchers(
                                        "/api/v1/admin/**"
                                        )
                                        .hasAnyRole("ADMIN")
                                .requestMatchers(
                                        "/**")
                                        .permitAll()
                                .anyRequest().authenticated()
                )
            ;
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }
}
