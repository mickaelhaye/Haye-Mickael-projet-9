package com.mbackdiabete.microservicebackdiabete.controller;


import com.mbackdiabete.microservicebackdiabete.configuration.CustomProperties;
import com.mbackdiabete.microservicebackdiabete.model.dto.PatientInfoDTO;
import com.mbackdiabete.microservicebackdiabete.service.CalculService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


/**
 * Contrôleur pour gérer les requêtes liées au diabète dans le microservice back-diabete.
 * Fournit des endpoints pour interagir avec les informations liées aux patients et aux calculs de risque de diabète.
 *
 * @author Mickael Hayé
 * @version 1.0
 */
@RestController
@RequestMapping("diabeteBack")
public class DiabeteController {

    private static final Logger logger = LoggerFactory.getLogger(DiabeteController.class);
    private final RestTemplate restTemplate;
    private final CustomProperties prop;
    @Autowired
    private CalculService CalculService;

    /**
     * Constructeur pour injecter les dépendances nécessaires dans le contrôleur.
     *
     * @param restTemplate Le RestTemplate utilisé pour les appels HTTP.
     * @param prop         Les propriétés personnalisées du microservice.
     */
    @Autowired
    public DiabeteController(RestTemplate restTemplate, CustomProperties prop) {
        this.restTemplate = restTemplate;
        this.prop = prop;
    }

    /**
     * Endpoint pour obtenir le risque de diabète d'un patient.
     * Récupère les informations du patient et ses notes médicales, puis calcule le risque de diabète.
     *
     * @param id         L'identifiant unique du patient.
     * @param authHeader L'en-tête d'autorisation pour les requêtes HTTP.
     * @return String - Le niveau de risque de diabète du patient.
     */
    @GetMapping(value = "/risque/{id}")
    public String risqueDiabete(@PathVariable String id, @RequestHeader("Authorization") String authHeader) {
        logger.info("Récupération de la date d'anniversaire et du genre pour le patient avec l'ID: {}", id);
        String url = prop.getGatewayPath() + "/patientBack/getInfoDiabete/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<PatientInfoDTO> responseDate = restTemplate.exchange(url, HttpMethod.GET, entity, PatientInfoDTO.class);
        PatientInfoDTO patientInfoDTO = responseDate.getBody();

        logger.info("Récupération de la liste de notes pour le patient avec l'ID: {}", id);
        url = prop.getGatewayPath() + "/noteBack/getListeNotes/" + id;
        ResponseEntity<String[]> responseNotes = restTemplate.exchange(url, HttpMethod.GET, entity, String[].class);
        List<String> notes = Arrays.asList(responseNotes.getBody());

        return CalculService.getNiveauxRisque(patientInfoDTO.getDateDeNaissance(), patientInfoDTO.getGenre(), notes);
    }
}