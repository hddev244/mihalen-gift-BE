package shop.mihalen.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;


public class UserPrincipalAuthenticationToken  extends AbstractAuthenticationToken {
    private final AccountPrincipal principal;

    public UserPrincipalAuthenticationToken(AccountPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
       return principal;
    }
}
