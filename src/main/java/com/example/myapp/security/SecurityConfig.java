package com.example.myapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;
@Bean
public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{

    httpSecurity.authorizeRequests()
            .mvcMatchers("../bootstrap/**","/img/**","/home","/").permitAll()
            .mvcMatchers("/shop/**","/cart/**","/customer/**").permitAll()
            .anyRequest()
            .authenticated();

    httpSecurity.formLogin()
            .defaultSuccessUrl("/home")
            .and()
            .csrf()
            .disable();

   return httpSecurity.build();
}


@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}

@Bean
public DaoAuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(customUserDetailService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}


}
