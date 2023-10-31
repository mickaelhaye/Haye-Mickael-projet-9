/**
 * Package contenant les configurations pour le microservice gateway.
 */
package com.mgateway.microservicegateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuration de sécurité pour le microservice gateway.
 * <p>
 * Cette classe configure la sécurité pour le microservice en utilisant
 * Spring WebFlux Security. Elle définit les règles d'accès, les utilisateurs
 * et leurs rôles, ainsi que le mécanisme d'authentification.
 * </p>
 *
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    /**
     * Configure les règles d'accès pour les différentes URL de l'application.
     *
     * @param http Une instance de {@link ServerHttpSecurity} utilisée pour configurer la sécurité.
     * @return La chaîne de filtres de sécurité configurée.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .pathMatchers("/patientFront/**").authenticated()
                .pathMatchers("/patientBack/**").authenticated()
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        return http.build();
    }

    /**
     * Configure les détails de l'utilisateur pour l'authentification.
     *
     * @return Une instance de {@link MapReactiveUserDetailsService} avec les détails de l'utilisateur.
     */
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("0241585915"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    /**
     * Fournit une instance de l'encodeur de mot de passe pour la sécurité.
     *
     * @return Une instance de {@link BCryptPasswordEncoder}.
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
