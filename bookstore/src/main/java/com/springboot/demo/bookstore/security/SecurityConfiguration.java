package com.springboot.demo.bookstore.security;

import com.springboot.demo.bookstore.service.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class manages the security aspect of the application
 * by enforcing authentication and authorization before any API call
 */
@Slf4j
@EnableWebSecurity
@Configuration
@Profile("!test")
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)  // to enable post methods, so that postman can login.
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(registry-> {
                    registry.requestMatchers(HttpMethod.GET, "/books").permitAll()
                            .requestMatchers(HttpMethod.POST, "/books").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.DELETE, "/books/**").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/books").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.PATCH, "/books").hasAnyRole("ADMIN", "USER")
                            .anyRequest().authenticated();
                })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailServiceImpl;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
