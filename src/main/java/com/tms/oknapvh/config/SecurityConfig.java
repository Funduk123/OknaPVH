package com.tms.oknapvh.config;

import com.tms.oknapvh.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/store", "/store/search", "/store/register", "/store/sign-in", "/store/window-details/**", "store/profile/*").permitAll()
                .antMatchers("/store/orders/**", "/store/profile/").authenticated()
                .antMatchers("/store/redactor/**", "/store/users-list").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/store/sign-in")
                .loginProcessingUrl("/store/sign-in")
                .failureUrl("/store/sign-in?error=true")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .defaultSuccessUrl("/store", false)
                .and()
                .logout()
                .logoutUrl("/store/logout")
                .and()
                .rememberMe()
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400) // 1 день
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
