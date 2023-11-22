/**
 * Package dédié à la configuration du microservice front.
 */
package com.mfront.microservicefront.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe de configuration pour la sécurité web du microservice front.
 * Cette classe configure la sécurité web en utilisant Spring Security.
 * Elle est annotée avec {@code @EnableWebSecurity} pour activer la sécurité web et
 * {@code @Configuration} pour indiquer à Spring que c'est une classe de configuration.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    /**
     * Configure et retourne un {@code SecurityFilterChain} pour la sécurité HTTP.
     *
     * @param http l'objet {@code HttpSecurity} pour configurer la sécurité web.
     * @return le {@code SecurityFilterChain} configuré.
     * @throws Exception en cas d'erreurs pendant la configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuration de la sécurité HTTP.");
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf().disable();
        return http.build();
    }

    /**
     * Configure et retourne un service de détails d'utilisateur en mémoire.
     * Cette méthode configure un utilisateur de test en mémoire pour l'authentification.
     *
     * @return le {@code UserDetailsService} configuré.
     */
    @Bean
    public UserDetailsService users() {
        logger.info("Configuration des utilisateurs en mémoire.");
        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("0241585915"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }

    /**
     * Configure et retourne un gestionnaire d'authentification.
     *
     * @param authenticationConfiguration la configuration d'authentification.
     * @return le {@code AuthenticationManager} configuré.
     * @throws Exception en cas d'erreurs pendant la configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.info("Configuration du gestionnaire d'authentification.");
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Retourne un encodeur de mot de passe BCrypt.
     *
     * @return le {@code PasswordEncoder} configuré.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        logger.info("Configuration de l'encodeur de mot de passe BCrypt.");
        return new BCryptPasswordEncoder();
    }
}
