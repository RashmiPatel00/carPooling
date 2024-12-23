package com.smartcontactmanager.smartcontactmanager.config;

import com.smartcontactmanager.smartcontactmanager.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MyConfig {

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl(); // Your custom UserDetailsService implementation
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-specific routes
                .requestMatchers("/driver/**").hasRole("DRIVER") // Driver-specific routes
                .requestMatchers("/user/**").hasRole("USER") // User-specific routes
                .requestMatchers("/", "/signup", "/signin", "/dologin").permitAll() // Public routes
                .anyRequest().authenticated() // Protect all other routes
            )
            .formLogin(form -> form
                .loginPage("/signin") // Custom login page
                .loginProcessingUrl("/dologin") // Form action for processing login
                .defaultSuccessUrl("/dashboard", true) // Redirect based on roles after successful login
                .failureUrl("/signin?error=true") // Redirect if login fails
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Logout URL
                .logoutSuccessUrl("/signin?logout=true") // Redirect after logout
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for development (enable for production)
        return http.build();
    }
}
