/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.privateParking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // PASSO 1: DESABILITAR CSRF COMPLETAMENTE
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                // Mantemos as permissões públicas
                .requestMatchers("/css/**", "/js/**", "/images/**", "/bootstrap/**").permitAll()
                .requestMatchers("/", "/acesso-negado", "/redefinir-senha").permitAll() // Mantém a rota pública

                // Mantemos as permissões por role
                .requestMatchers("/relatorio", "/api/relatorio", "/faturamento", "/api/faturamento").hasRole("GERENTE")
                .requestMatchers("/menu", "/cadastroCliente", "/pedido/**", "/api/pedido/**").hasAnyRole("ATENDENTE", "GERENTE")
                // Qualquer outra requisição precisa de autenticação
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("usuario")
                .passwordParameter("senha")
                .defaultSuccessUrl("/menu", true)
                .failureUrl("/?error=true")
                .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exception -> exception
                .accessDeniedPage("/acesso-negado")
                );

        return http.build();

    }
}
