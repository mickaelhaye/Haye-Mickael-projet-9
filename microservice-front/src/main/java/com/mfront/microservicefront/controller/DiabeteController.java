/**
 * Package dédié aux contrôleurs du microservice front.
 */
package com.mfront.microservicefront.controller;

import com.mfront.microservicefront.configuration.CustomProperties;
import com.mfront.microservicefront.model.PatientModel;
import com.mfront.microservicefront.service.DateService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Contrôleur pour la gestion des requêtes liées au diabète dans le microservice front.
 * Ce contrôleur fournit des fonctionnalités pour interagir avec le microservice back-end et récupérer des informations sur le risque de diabète des patients.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Controller
@RequestMapping("diabeteFront")
public class DiabeteController {
    private static final Logger logger = LoggerFactory.getLogger(DiabeteController.class);
    private final RestTemplate restTemplate;
    private final CustomProperties prop;

    @Autowired
    private DateService  dateService;

    /**
     * Constructeur pour initialiser DiabeteController avec les dépendances nécessaires.
     *
     * @param restTemplate Le RestTemplate pour effectuer des appels HTTP.
     * @param prop Les propriétés personnalisées pour la configuration.
     */
    @Autowired
    public DiabeteController(RestTemplate restTemplate, CustomProperties prop) {
        this.restTemplate = restTemplate;
        this.prop = prop;
    }

    /**
     * Gère les requêtes GET pour obtenir l'évaluation du risque de diabète d'un patient.
     * L'ID du patient est passé en tant que variable de chemin et la méthode interroge le microservice back-end pour obtenir l'évaluation du risque.
     *
     * @param id L'identifiant unique du patient.
     * @param model Le modèle Spring MVC.
     * @param authHeader L'en-tête d'autorisation pour les requêtes HTTP.
     * @return Une chaîne de caractères représentant le niveau de risque de diabète du patient.
     */
    @GetMapping("/risque/{id}")
    public String risqueDiabete(@PathVariable String id, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Récupération du formulaire de mise à jour pour le patient avec l'ID: {}", id);
        String url = prop.getGatewayPath() + "/diabeteBack/risque/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String risque =  response.getBody();
        return risque;
    }

}