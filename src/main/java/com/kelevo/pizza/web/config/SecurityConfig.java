package com.kelevo.pizza.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permitir autenticación
                        .requestMatchers("/auth/**").permitAll()
                        // Pizzas
                        .requestMatchers(HttpMethod.GET, "/pizzas/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/pizzas/**").hasRole("ADMIN")
                        // Customers
                        .requestMatchers("/customer/**").hasAnyRole("ADMIN", "CUSTOMER")
                        // Orders
                        .requestMatchers("/orders/random").hasAuthority("random_order")
                        .requestMatchers("/orders/**").hasRole("ADMIN")
                        // Todos los put solo permitidos por ADMIN
                        .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                        // Cualquier otro endpoint requiere autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}
