package com.example.foodcloud;

import com.example.foodcloud.security.filter.AccessDeniedHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 인증 or 인가에 대한 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/user/my-page")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                .and()
                    .authorizeRequests()
                    .antMatchers("/user/login", "/user/join").permitAll()
                    .antMatchers("/user/**", "/user", "/restaurant/**", "/new-order/**").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
                    .build();
    }
}