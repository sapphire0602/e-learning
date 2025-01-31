package com.ahmad.e_learning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true ,  securedEnabled = true)
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtFilter;
    private final SuccessHandler successHandler;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtFilter, SuccessHandler successHandler) {
        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        request -> request.requestMatchers( "/api/v1/register" , "/api/v1/user/all" , "/api/v1/user/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
//                .oauth2Login(oauth2 ->
//                        oauth2
//                                //.loginPage("/oauth2/authorization/github")
//                                .defaultSuccessUrl("/api/v1/user/all", true)
//                                .failureUrl("/api/v1/register")
//                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form.successHandler(successHandler).defaultSuccessUrl("/api/v1/user/all"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
