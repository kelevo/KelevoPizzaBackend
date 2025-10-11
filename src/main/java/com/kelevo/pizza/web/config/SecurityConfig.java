package com.kelevo.pizza.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Pizzas
                        .requestMatchers(HttpMethod.GET, "/pizzas/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/pizzas/**").hasRole("ADMIN")
                        // Customers
                        .requestMatchers(HttpMethod.GET, "/customer/**").hasRole("ADMIN")
                        // Orders
                        .requestMatchers("/orders/random").hasAuthority("random_order")
                        .requestMatchers("/orders/**").hasRole("ADMIN")
                        // Permitir autenticación y documentación
                        .requestMatchers("/auth/**", "/docs/**").permitAll()
                        // Todos los put solo permitidos por ADMIN
                        .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                        // Cualquier otro endpoint requiere autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}
