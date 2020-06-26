package com.inspur.system.security.config;

import com.inspur.system.security.filter.JwtLoginAuthenticationFilter;
import com.inspur.system.security.filter.JwtRequestAuthorizationFilter;
import com.inspur.system.security.token.TokenRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 自定义web安全配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 认证管理器
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    /**
     * 自定义登陆请求身份验证提供程序
     */
    @Autowired
    @Qualifier("jwtLoginAuthenticationProvider")
    private AuthenticationProvider jwtLoginAuthenticationProvider;

    /**
     * 登陆请求认证成功的处理器
     */
    @Autowired
    @Qualifier("jwtLoginAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler jwtLoginAuthenticationSuccessHandler;
    /**
     * 登陆请求认证失败的处理器
     */
    @Autowired
    @Qualifier("jwtLoginAuthenticationFailureHandler")
    private AuthenticationFailureHandler jwtLoginAuthenticationFailureHandler;
    /**
     * token工具类
     */
    @Autowired
    private TokenRedisUtil tokenRedisUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtLoginAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/user/login").authenticated().anyRequest().authenticated()
                // token 访问,关闭session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtRequestAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 解决禁止嵌入iframe的问题，
        http.headers().frameOptions().disable()
                // 关闭csrf攻击防御
                .and().csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtLoginAuthenticationFilter jwtLoginAuthenticationFilter() {
        JwtLoginAuthenticationFilter filter = new JwtLoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(jwtLoginAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(jwtLoginAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    public JwtRequestAuthorizationFilter jwtRequestAuthorizationFilter() {
        JwtRequestAuthorizationFilter filter = new JwtRequestAuthorizationFilter(authenticationManager, tokenRedisUtil);
        return filter;
    }

}
