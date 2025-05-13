package com.paccy.eucl.config;


import com.paccy.eucl.security.JwtAuthFilter;
import com.paccy.eucl.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(
                AbstractHttpConfigurer::disable
        )
                .authorizeHttpRequests(
                        request->
                                request
                                        .requestMatchers("/api/v1/token/purchase",
                                                "/api/v1/user/admin/register",
                                                "/api/v1/meter-number/**",
                                                "/api/v1/token/**")
                                        .authenticated()
                                        .anyRequest()
                                        .permitAll()
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return  http.build();

  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler(){
    return (request, response, accessDeniedException) -> {

    }   ;
  }

}
