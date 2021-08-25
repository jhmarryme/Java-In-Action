package com.imooc.uaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.uaa.security.filter.RestAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
@Order(100)
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin(login -> login
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
//                .failureHandler(jsonLoginFailureHandler())
//                .successHandler(new UaaSuccessHandler())
                .permitAll())
            .logout(logout -> logout
                    .logoutUrl("/perform_logout")
                    .logoutSuccessUrl("/login")
//                .logoutSuccessHandler(jsonLogoutSuccessHandler())
            )
            .rememberMe(rememberMe -> rememberMe
                .key("someSecret")
                .tokenValiditySeconds(86400))
            .authorizeRequests(authorizeRequests -> authorizeRequests
                .anyRequest().authenticated());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


}


