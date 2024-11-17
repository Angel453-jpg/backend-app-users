package com.springboot.backend.angel.usersapp.auth;

import com.springboot.backend.angel.usersapp.auth.filter.JwtAuthenticationFilter;
import com.springboot.backend.angel.usersapp.auth.filter.JwtValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authz ->
                        authz.requestMatchers(HttpMethod.GET, "/api/users", "/api/users/page/{page}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
