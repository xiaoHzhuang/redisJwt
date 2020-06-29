package com.inspur.system.security.provider;

import com.inspur.constant.Constant;
import com.inspur.system.security.token.JwtAuthenticationToken;
import com.inspur.system.utils.DesPassword;
import com.inspur.system.utils.DesTools;
import com.inspur.system.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtLoginAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService systemUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails systemUserDetails = systemUserDetailService.loadUserByUsername(userName);
        if (StringUtils.isEmpty(systemUserDetails.getPassword())) {
            throw new BadCredentialsException(Constant.LOGIN_ERROR);
        }
        JwtAuthenticationToken jwtAuthenticationToken = null;
        DesPassword desPassword = new DesPassword();
        password = desPassword.strDec(password);
        String pwdInDb = systemUserDetails.getPassword();
        pwdInDb = DesTools.decryptPassword(pwdInDb);
        if (pwdInDb.equals(password)) {
            jwtAuthenticationToken = new JwtAuthenticationToken(systemUserDetails, password, systemUserDetails.getAuthorities());
        } else {
            //用户名或者密码不对
            throw new BadCredentialsException(Constant.LOGIN_ERROR);
        }
        return jwtAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.getName().equals(JwtAuthenticationToken.class.getName());
    }
}
