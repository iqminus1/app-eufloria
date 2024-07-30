package uz.pdp.appeufloria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appeufloria.entity.templates.AbsIntEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity(name = "users")
public class User extends AbsIntEntity implements UserDetails, Serializable {

    private String username;

    private String password;

    private String email;

    private boolean enable;

    @ManyToOne
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null || role.getPermissions() == null) {
            return new ArrayList<>();
        }
        return role.getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .toList();
    }

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

}
