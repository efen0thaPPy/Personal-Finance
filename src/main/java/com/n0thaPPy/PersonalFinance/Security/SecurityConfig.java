package com.n0thaPPy.PersonalFinance.Security;

import com.n0thaPPy.PersonalFinance.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
 private MyUserDetailsService service;

    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

        security.authorizeHttpRequests(
                e->e
                        .requestMatchers("/register","/login").permitAll()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/swagger-resources/**",
                                "/swagger-resources",
                                "/webjars/**",
                                "/configuration/ui",
                                "/configuration/security"
                        ).permitAll().anyRequest().authenticated())

                .sessionManagement(e->e.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .csrf(e->e.disable())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);




       return security.build();
    }
    @Bean
    public AuthenticationManager manager(HttpSecurity security) throws Exception {

       AuthenticationManagerBuilder manager= security.getSharedObject(AuthenticationManagerBuilder.class);
       manager.userDetailsService(service).passwordEncoder(passwordEncoder());
       return manager.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
      return new BCryptPasswordEncoder(12);
    }
}
