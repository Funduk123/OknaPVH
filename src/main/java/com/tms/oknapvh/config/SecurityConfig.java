package com.tms.oknapvh.config;

import com.tms.oknapvh.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/store", "/store/search", "/store/register", "/store/sign-in", "/store/window-details/**").permitAll()
                .antMatchers("/store/orders/**", "/store/profile/**").authenticated()
                .antMatchers("/store/redactor/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/store/sign-in").loginProcessingUrl("/store/sign-in")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .defaultSuccessUrl("/store", false)
                .and()
                .logout()
                .logoutUrl("/store/logout")
                .addLogoutHandler((request, response, authentication) -> {
                    try {
                        response.sendRedirect("/store");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

}