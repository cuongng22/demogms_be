package com.example.demo.request;

import com.example.demo.table.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserCustom implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private List<? extends GrantedAuthority> roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


    public static UserCustom build(Users user) {
        List<GrantedAuthority> authorities = new ArrayList<>(); //tạo một list quyền cho user principal
        return new UserCustom(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public UserCustom(Long id, String username, String password, List<? extends GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
