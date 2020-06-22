package com.inspur.system.security.provider;

import com.inspur.system.security.token.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService systemUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails systemUserDetails = systemUserDetailService.loadUserByUsername(userName);
        JwtAuthenticationToken jwtAuthenticationToken = null;
        if (systemUserDetails.getPassword().equals(password)) {
            jwtAuthenticationToken = new JwtAuthenticationToken(systemUserDetails, password, systemUserDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("用户名或者密码不对");
        }
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.getName().equals(JwtAuthenticationToken.class.getName());
    }
}
