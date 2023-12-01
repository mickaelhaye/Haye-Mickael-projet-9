package com.mfront.microservicefront.controller;

import com.mfront.microservicefront.configuration.CustomProperties;
import com.mfront.microservicefront.model.NoteModel;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

/**
 * Contrôleur pour la gestion des notes.
 * Gère les requêtes liées aux notes des patients.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Controller
@RequestMapping("noteFront")
public class NoteController {
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    private final RestTemplate restTemplate;
    private final CustomProperties prop;

    private String idPatient;
    private String nomPatient;

    /**
     * Constructeur pour l'injection de dépendances.
     *
     * @param restTemplate Objet RestTemplate pour les appels HTTP.
     * @param prop         Objets de propriétés personnalisées.
     */
    @Autowired
    public NoteController(RestTemplate restTemplate, CustomProperties prop) {
        this.restTemplate = restTemplate;
        this.prop = prop;
    }

    /**
     * Liste toutes les notes pour un patient donné.
     *
     * @param id         Identifiant du patient.
     * @param nom        Nom du patient.
     * @param model      Modèle de Spring MVC.
     * @param authHeader En-tête d'autorisation.
     * @return Vue pour afficher la liste des notes.
     */
    @GetMapping("/list/{id}/{nom}")
    public String listNote(@PathVariable String id,@PathVariable String nom, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Liste des notes demandée pour le patient ID: {}, Nom: {}", id, nom);
        idPatient = id;
        nomPatient = nom;
        String url = prop.getGatewayPath() + "/noteBack/list/"+idPatient;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<NoteModel[]> response;
        try{
            response = restTemplate.exchange(url, HttpMethod.GET, entity, NoteModel[].class);
        }catch (RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service de notes.");
            return "error/errorPage";
        }
        List<NoteModel> notes = Arrays.asList(response.getBody());
        model.addAttribute("notes", notes);
        model.addAttribute("nomPatient", nomPatient);
        model.addAttribute("idPatient", idPatient);
        return "note/list";
    }

    /**
     * Formulaire de mise à jour pour une note spécifique.
     *
     * @param id         Identifiant de la note.
     * @param model      Modèle de Spring MVC.
     * @param authHeader En-tête d'autorisation.
     * @return Vue pour la mise à jour de la note.
     */
    @GetMapping("/updateForm/{id}")
    public String updateNoteForm(@PathVariable String id, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Formulaire de mise à jour demandé pour la note ID: {}", id);
        String url = prop.getGatewayPath() + "/noteBack/updateForm/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<NoteModel> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, NoteModel.class);
        }catch (RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service de notes.");
            return "error/errorPage";
        }
        model.addAttribute("note", response.getBody());
        model.addAttribute("nomPatient", nomPatient);
        model.addAttribute("idPatient", idPatient);
        return "note/update";
    }

    /**
     * Met à jour une note spécifique.
     *
     * @param id         Identifiant de la note à mettre à jour.
     * @param note       Objet NoteModel contenant les informations de la note mise à jour.
     * @param model      Modèle de Spring MVC.
     * @param authHeader En-tête d'autorisation pour l'authentification.
     * @return Redirection vers la liste des notes du patient concerné.
     */
    @PostMapping("/update/{id}")
    public String updateNote(@PathVariable String id, @Valid NoteModel note, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Mise à jour de la note ID: {}", id);
        note.setIdPatient(idPatient);
        String url = prop.getGatewayPath() + "/noteBack/update/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<NoteModel> entity = new HttpEntity<>(note, headers);
        try{restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        }catch (RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service de notes.");
            return "error/errorPage";
        }
        return "redirect:" + prop.getGatewayPath() + "/noteFront/list/"+idPatient+"/"+nomPatient;
    }

    /**
     * Affiche le formulaire pour ajouter une nouvelle note.
     *
     * @param model Modèle de Spring MVC.
     * @return Vue pour ajouter une nouvelle note.
     */
    @GetMapping("/add")
    public String addNoteForm(Model model) {
        logger.info("Formulaire d'ajout de note demandé");
        NoteModel note = new NoteModel();
        note.setIdPatient(idPatient);
        model.addAttribute("note", note);
        model.addAttribute("nomPatient", nomPatient);
        model.addAttribute("idPatient", idPatient);
        return "note/add";
    }

    /**
     * Ajoute une nouvelle note pour un patient.
     *
     * @param note       Objet NoteModel contenant les détails de la nouvelle note.
     * @param model      Modèle de Spring MVC.
     * @param authHeader En-tête d'autorisation pour l'authentification.
     * @return Redirection vers la liste des notes du patient concerné.
     */
    @PostMapping("/add")
    public String addNote(@Valid NoteModel note, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Ajout d'une nouvelle note en cours");
        note.setId(null);
        note.setIdPatient(idPatient);
        String url = prop.getGatewayPath() + "/noteBack/add";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<NoteModel> entity = new HttpEntity<>(note, headers);
        try{restTemplate.postForEntity(url, entity, Void.class);
        }catch (RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service de notes.");
            return "error/errorPage";
        }
        return "redirect:" + prop.getGatewayPath() + "/noteFront/list/"+idPatient+"/"+nomPatient;
    }

    /**
     * Supprime une note spécifique.
     *
     * @param id         Identifiant de la note à supprimer.
     * @param authHeader En-tête d'autorisation pour l'authentification.
     * @return Redirection vers la liste des notes du patient concerné.
     */
    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable String id, Model model, @RequestHeader("Authorization") String authHeader) {
        logger.info("Demande de suppression de la note ID: {}", id);
        String url = prop.getGatewayPath() + "/noteBack/delete/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try{restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        }catch (RestClientException e) {
            logger.error("Erreur de connexion au microservice : " + e.getMessage());
            model.addAttribute("errorMessage", "Impossible de se connecter au service de notes.");
            return "error/errorPage";
        }
        return "redirect:" + prop.getGatewayPath() + "/noteFront/list/"+idPatient+"/"+nomPatient;
    }
}