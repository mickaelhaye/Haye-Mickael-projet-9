package com.mbacknote.microservicebacknote.configuration;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Classe de configuration pour les paramètres de sécurité de l'application.
 * Cette configuration définit divers beans liés à la sécurité tels que UserDetailsService,
 * AuthenticationManager, et PasswordEncoder.
 * @author mickael hayé
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    /**
     * Configure les paramètres HttpSecurity pour l'application.
     *
     * @param http L'instance HttpSecurity à configurer.
     * @return SecurityFilterChain configuré.
     * @throws Exception si une erreur se produit lors de la configuration.
     */
    //@formatter:off
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuration de HttpSecurity.");
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf().disable();
        logger.info("HttpSecurity configuré avec succès.");
        return http.build();
        //@formatter:on
    }

    /**
     * Fournit un UserDetailsService d'exemple avec un utilisateur prédéfini en mémoire.
     *
     * @return UserDetailsService avec un utilisateur prédéfini.
     */
    @Bean
    public UserDetailsService users() {
        logger.info("Création du UserDetailsService avec un utilisateur prédéfini.");
        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("0241585915"))
                .roles("USER")
                .build();
        logger.info("Utilisateur 'user1' créé et ajouté à la mémoire.");
        return new InMemoryUserDetailsManager(user1);
    }

    /**
     * Renvoie le bean AuthenticationManager de la configuration d'authentification fournie.
     *
     * @param authenticationConfiguration L'instance AuthenticationConfiguration.
     * @return bean AuthenticationManager.
     * @throws Exception si une erreur se produit lors de la récupération.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.info("Récupération du bean AuthenticationManager.");
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Fournit un bean PasswordEncoder pour l'application.
     *
     * @return bean PasswordEncoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        logger.info("Configuration du BCryptPasswordEncoder.");
        return new BCryptPasswordEncoder();
    }
}