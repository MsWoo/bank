package com.bank.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Bank_users")

public class BankUser implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String username;

    private String fromSocial;

    private Long balance;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String roles = getRoles().toString();
        String[] authStrings = roles.substring(1, roles.length() - 1).split(", ");
        for (String authString : authStrings) {
            authorities.add(new SimpleGrantedAuthority(authString));
        }
        return authorities;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();

    public void addUserRole(UserRole userRole) {
        roles.add(userRole);
    }

    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    @Transient
    private boolean enabled = true;

}
