package shop.mihalen.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import shop.mihalen.servive.AccountService;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private  AccountService accountService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountService.findByUsername(username).orElseThrow();

        Collection<GrantedAuthority> authorities =  account.getRoles().stream()
                                                            .map(role -> new SimpleGrantedAuthority(role.getId()))
                                                            .collect(Collectors.toList());
                                                       
        return AccountPrincipal.builder()
                .userId(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .email(account.getEmail())
                .locked(account.isLocked())
                .authorities(authorities)
                .build();
    }
}
