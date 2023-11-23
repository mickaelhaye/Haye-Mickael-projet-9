/**
 * Package dédié aux contrôleurs du microservice front.
 */
package com.mfront.microservicefront.controller;

import com.mfront.microservicefront.configuration.CustomProperties;
import com.mfront.microservicefront.model.PatientModel;
import com.mfront.microservicefront.service.DateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contrôleur gérant les interactions liées aux patients pour le front-end.
 * <p>
 * Cette classe gère les routes relatives aux patients, notamment l'affichage, l'ajout,
 * la mise à jour et la suppression des patients.
 * </p>
 *
 * @author mickael hayé
 * @version 1.0
 */
@Controller
@RequestMapping("patientFront")
public class PatientController {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    private final RestTemplate restTemplate;
    private final CustomProperties prop;

    @Autowired
    private DateService  dateService;

    /**
     * Constructeur permettant l'injection des dépendances.
     *
     * @param restTemplate Le bean {@code RestTemplate} pour les appels HTTP.
     * @param prop         Les propriétés personnalisées du microservice.
     */
    @Autowired
    public PatientController(RestTemplate restTemplate, CustomProperties prop) {
        this.restTemplate = restTemplate;
        this.prop = prop;
    }

    /**
     * Affiche la liste des patients.
     *
     * @param model      Modèle Spring pour passer des données à la vue.
     * @param authHeader En-tête d'autorisation pour la requête.
     * @return Le nom de la vue pour afficher la liste des patients.
     */
    @GetMapping("/list")
    public String listPatient(Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Récupération de la liste des patients.");
        String url = prop.getGatewayPath() + "/patientBack/list";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<PatientModel[]> response;
        try{response = restTemplate.exchange(url, HttpMethod.GET, entity, PatientModel[].class);
        }catch (
        RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service de patients.");
            return "error/errorPage";
        }
        List<PatientModel> patients = Arrays.asList(response.getBody());
        model.addAttribute("patients", patients);
        return "patient/list";
    }

    /**
     * Affiche le formulaire de mise à jour pour un patient donné.
     *
     * @param id         ID du patient à mettre à jour.
     * @param model      Modèle Spring pour passer des données à la vue.
     * @param authHeader En-tête d'autorisation pour la requête.
     * @return Le nom de la vue pour la mise à jour du patient.
     */
    @GetMapping("/updateForm/{id}")
    public String updatePatientForm(@PathVariable String id, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Récupération du formulaire de mise à jour pour le patient avec l'ID: {}", id);
        String url = prop.getGatewayPath() + "/patientBack/updateForm/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<PatientModel> response;
        try{response = restTemplate.exchange(url, HttpMethod.GET, entity, PatientModel.class);
        }catch (
        RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service de patients.");
            return "error/errorPage";
        }

        logger.info("Récupération du apport de diabète pour le patient avec l'ID: {}", id);
        url = prop.getGatewayPath() + "/diabeteBack/risque/" + id;
        ResponseEntity<String> responseString;
        try{ responseString = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        }catch (
        RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service de diabete ou notes.");
            return "error/errorPage";
        }
        String rapportDiabete =responseString.getBody();

        model.addAttribute("patient", response.getBody());
        model.addAttribute("dateDuJour", dateService.dateDuJour());
        model.addAttribute("rapportDiabete", rapportDiabete);
        return "patient/update";
    }

    /**
     * Met à jour les informations d'un patient.
     *
     * @param id         ID du patient à mettre à jour.
     * @param patient    Les nouvelles informations du patient.
     * @param model      Modèle Spring pour passer des données à la vue.
     * @param authHeader En-tête d'autorisation pour la requête.
     * @return Redirige vers la liste des patients après la mise à jour.
     */
    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable String id, @Valid PatientModel patient, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Mise à jour du patient avec l'ID: {}", id);
        String url = prop.getGatewayPath() + "/patientBack/update/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<PatientModel>entity = new HttpEntity<>(patient, headers);
        try{restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        }catch (
        RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service patients.");
            return "error/errorPage";
        }
        return "redirect:" + prop.getGatewayPath() + "/patientFront/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un nouveau patient.
     *
     * @param model Modèle Spring pour passer des données à la vue.
     * @return Le nom de la vue pour ajouter un patient.
     */
    @GetMapping("/add")
    public String addPatientForm(Model model) {
        logger.info("Affichage du formulaire pour ajouter un nouveau patient.");
        model.addAttribute("patient", new PatientModel());
        model.addAttribute("dateDuJour", dateService.dateDuJour());
        return "patient/add";
    }

    /**
     * Ajoute un nouveau patient.
     *
     * @param patient    Les informations du nouveau patient.
     * @param model      Modèle Spring pour passer des données à la vue.
     * @param authHeader En-tête d'autorisation pour la requête.
     * @return Redirige vers la liste des patients après l'ajout.
     */
    @PostMapping("/add")
    public String addPatient(@Valid PatientModel patient, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Ajout d'un nouveau patient.");
        patient.setId(null);
        String url = prop.getGatewayPath() + "/patientBack/add";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<PatientModel> entity = new HttpEntity<>(patient, headers);
        try{restTemplate.postForEntity(url, entity, Void.class);
        }catch (
        RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service patients.");
            return "error/errorPage";
        }
        return "redirect:" + prop.getGatewayPath() + "/patientFront/list";
    }

    /**
     * Supprime un patient.
     *
     * @param id         ID du patient à supprimer.
     * @param authHeader En-tête d'autorisation pour la requête.
     * @return Redirige vers la liste des patients après la suppression.
     */
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Suppression du patient avec l'ID: {}", id);
        String url = prop.getGatewayPath() + "/patientBack/delete/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try{restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        }catch (
        RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service patients.");
            return "error/errorPage";
        }

        //Suppression des notes concernant le patient
        url = prop.getGatewayPath() + "/noteBack/deleteAll/" + id;
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        return "redirect:" + prop.getGatewayPath() + "/patientFront/list";
    }
}