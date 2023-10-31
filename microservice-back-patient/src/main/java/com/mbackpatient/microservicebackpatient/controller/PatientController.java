package com.mbackpatient.microservicebackpatient.controller;

import com.mbackpatient.microservicebackpatient.exceptions.PatientNotFoundException;
import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import com.mbackpatient.microservicebackpatient.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


/**
 * Point d'entrée REST pour la gestion des patients.
 * @author mickael hayé
 * @version 1.0
 */
@RestController
@RequestMapping("patientBack")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);
    /**
     * Service pour les opérations liées au patient.
     */
    @Autowired
    private PatientService patientService;

    /**
     * Récupère la liste de tous les patients.
     *
     * @param authHeader En-tête d'autorisation pour authentifier la requête.
     * @return La liste des patients.
     */
    @GetMapping(value = "/list")
    public List<PatientModel> listPatient(@RequestHeader("Authorization") String authHeader) {
        logger.info("Récupération de la liste des patients avec l'autorisation: {}", authHeader);
        List<PatientModel> patients = (List<PatientModel>) patientService.getPatients();
        return patients;
    }

    /**
     * Récupère le formulaire de mise à jour pour un patient spécifique.
     *
     * @param id         L'identifiant du patient à mettre à jour.
     * @param authHeader En-tête d'autorisation pour authentifier la requête.
     * @return Les détails du patient à mettre à jour.
     * @throws PatientNotFoundException si le patient n'est pas trouvé.
     */
    @GetMapping(value = "/updateForm/{id}")
    public Optional<PatientModel> updatePatientForm(@PathVariable int id, @RequestHeader("Authorization") String authHeader) {
        logger.info("Récupération du formulaire de mise à jour pour le patient ID: {} avec l'autorisation: {}", id, authHeader);
        Optional<PatientModel> patient = patientService.getPatientById(id);
        if (!patient.isPresent()) {
            logger.error("Patient avec ID: {} non trouvé", id);
            throw new PatientNotFoundException("Le patient correspondant à l'id " + id + " n'existe pas");
        }
        return patient;
    }

    /**
     * Met à jour les informations d'un patient.
     *
     * @param updatedPatient Les nouvelles informations du patient.
     * @param authHeader     En-tête d'autorisation pour authentifier la requête.
     * @return Le patient mis à jour.
     */
    @PostMapping(value = "/update/{id}")
    public PatientModel updatePatient(@RequestBody PatientModel updatedPatient, @RequestHeader("Authorization") String authHeader) {
        logger.info("Mise à jour du patient ID: {} avec l'autorisation: {}", authHeader);
        return patientService.addPatient(updatedPatient);
    }

    /**
     * Ajoute un nouveau patient.
     *
     * @param newPatient  Le nouveau patient à ajouter.
     * @param authHeader  En-tête d'autorisation pour authentifier la requête.
     * @return Le patient ajouté.
     */
    @PostMapping(value = "/add")
    public PatientModel addPatient(@RequestBody PatientModel newPatient, @RequestHeader("Authorization") String authHeader) {
        logger.info("Ajout d'un nouveau patient avec l'autorisation: {}", authHeader);
        return patientService.addPatient(newPatient);
    }

    /**
     * Supprime un patient spécifique.
     *
     * @param id         L'identifiant du patient à supprimer.
     * @param authHeader En-tête d'autorisation pour authentifier la requête.
     */
    @DeleteMapping(value = "/delete/{id}")
    public void deletePatient(@PathVariable int id, @RequestHeader("Authorization") String authHeader) {
        logger.info("Suppression du patient ID: {} avec l'autorisation: {}", id, authHeader);
        patientService.deletePatient(id);
    }
}
