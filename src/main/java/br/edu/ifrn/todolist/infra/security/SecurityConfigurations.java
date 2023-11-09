package br.edu.ifrn.todolist.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

  @Autowired
  private SecurityFilter securityFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeHttpRequests()
        .requestMatchers(HttpMethod.POST, "/login", "/users", "/").permitAll()
        .requestMatchers(HttpMethod.GET, "/swagger-ui/index.html", 
        "/swagger-ui.html", 
        "/swagger-ui/index.css", 
        "/swagger-ui/swagger-ui.css",
        "/swagger-ui/swagger-ui-standalone-preset.js",
        "/swagger-ui/swagger-initializer.js",
        "/swagger-ui/swagger-ui-bundle.js",
        "/v3/api-docs/swagger-config",
        "/v3/api-docs",
        "/v3/api-docs/",
        "/swagger-ui/favicon-16x16.png",
        "/swagger-ui/favicon-32x32.png").permitAll()
        .anyRequest().authenticated()
        .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}