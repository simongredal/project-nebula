package gruppe8.nebula.models;

import gruppe8.nebula.entities.AccountEntity;
import gruppe8.nebula.services.TeamService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Account implements UserDetails {
    private Long id;
    private String name;
    private String email;
    private String password;

    public Account() {}

    public static Account of(AccountEntity entity) {
        Account account = new Account();
        account.id = entity.id();
        account.email = entity.email();
        account.password = entity.password();
        account.name = entity.name();
        return account;
    }

    public Long id() { return id; }

    public String email() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String password() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String name() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() { return email; }

    @Override
    public String getPassword() { return password; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


