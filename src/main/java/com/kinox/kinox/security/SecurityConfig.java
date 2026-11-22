package com.kinox.kinox.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                        // Rotas públicas de autenticação e cadastro de clientes
                        .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()

                        // Catálogo público de consulta do cinema
                        .requestMatchers(HttpMethod.GET, "/filmes", "/filmes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/generos", "/generos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/salas", "/salas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/sessoes", "/sessoes/**").permitAll()

                        // Gerenciamento do cinema (exclusivo ADMIN)
                        .requestMatchers("/filmes", "/filmes/**").hasRole("ADMIN")
                        .requestMatchers("/generos", "/generos/**").hasRole("ADMIN")
                        .requestMatchers("/salas", "/salas/**").hasRole("ADMIN")
                        .requestMatchers("/sessoes", "/sessoes/**").hasRole("ADMIN")
                        .requestMatchers("/assentos", "/assentos/**").hasRole("ADMIN")
                        .requestMatchers("/sessoes_assentos", "/sessoes_assentos/**").hasRole("ADMIN")
                        .requestMatchers("/administradores", "/administradores/**").hasRole("ADMIN")

                        // Rotas de cliente autenticado
                        .requestMatchers("/clientes/me").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/clientes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/clientes").hasRole("ADMIN")
                        .requestMatchers("/clientes/**").hasAnyRole("CLIENTE", "ADMIN")

                        // Rotas de compras e ingressos
                        .requestMatchers(HttpMethod.GET, "/compras/minhas").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/compras").hasRole("ADMIN")
                        .requestMatchers("/compras", "/compras/**").hasAnyRole("CLIENTE", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/ingressos/meus").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/ingressos").hasRole("ADMIN")
                        .requestMatchers("/ingressos/**").hasAnyRole("CLIENTE", "ADMIN")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{"error":"Não autenticado ou token inválido."}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{"error":"Acesso negado para o seu perfil."}");
                        })
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
