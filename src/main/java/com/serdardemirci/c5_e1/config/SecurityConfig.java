package com.serdardemirci.c5_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .httpBasic()
        .and()
        .authorizeRequests()
//        .anyRequest().authenticated()
//        .anyRequest().permitAll()
//        .anyRequest().denyAll()
//        .anyRequest().hasAuthority("read")
//        .anyRequest().hasAnyAuthority("read", "ROLE_ADMIN")
//        .anyRequest().hasRole("ADMIN")
        .anyRequest().access("isAuthenticated() and hasAuthority('read') and hasRole('ROLE_ADMIN')")
        .and()
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    var userDetailsService = new InMemoryUserDetailsManager();

    var user1 = User
        .withUsername("serdar")
        .password(passwordEncoder().encode("12345"))
        .authorities("read")
        .roles("ADMIN")
        .build();

    var user2 = User
        .withUsername("armin")
        .password(passwordEncoder().encode("12345"))
        .authorities("read")
        .roles("USER")
        .build();

    userDetailsService.createUser(user1);
    userDetailsService.createUser(user2);

    return userDetailsService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
