package com.inspur.system.security.po;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SystemUserDetail implements UserDetails {
    private String userId;
    private String userName;
    private String password;
    private String userCaption;
    private Collection<? extends GrantedAuthority> getAuthorities;

    public SystemUserDetail(SystemUser systemUser) {
        if (systemUser != null) {
            this.userId = systemUser.getUserid();
            this.userName = systemUser.getUsername();
            this.password = systemUser.getPwd();
            this.userCaption = systemUser.getUsercaption();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCaption() {
        return userCaption;
    }

    public void setUserCaption(String userCaption) {
        this.userCaption = userCaption;
    }
}
