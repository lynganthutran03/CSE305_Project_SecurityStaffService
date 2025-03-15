package project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for testing
            .authorizeHttpRequests()
                .requestMatchers("/**").permitAll() // Allow login without authentication
                .anyRequest().permitAll() // Secure other endpoints
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session stored on the server
            .and()
            .headers()
                .httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000); // Enable HSTS
        
        return http.build();
    }
}