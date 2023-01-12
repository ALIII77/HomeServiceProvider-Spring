package com.example.ProjectV2.entity;

import com.example.ProjectV2.entity.enums.PersonType;
import com.example.ProjectV2.entity.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Admin extends Person implements UserDetails {

    public Admin(String firstName, String lastName, String email, String username, String password, LocalDateTime dateOfRegistration) {
        super(firstName, lastName, email, username, password, dateOfRegistration);
    }

    public Admin(String firstName, String lastName, String email, String username, String password) {
        super(firstName, lastName, email, username, password);
    }


    @PrePersist
    public void prePersist(){
        setPersonType(PersonType.ADMIN );
        setRole(Role.ROLE_ADMIN);
        setEnabled(false);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
