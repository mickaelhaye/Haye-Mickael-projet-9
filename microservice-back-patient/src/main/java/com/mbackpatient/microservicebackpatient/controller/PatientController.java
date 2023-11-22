package com.mbackpatient.microservicebackpatient.controller;

import com.mbackpatient.microservicebackpatient.exceptions.PatientNotFoundException;
import com.mbackpatient.microservicebackpatient.model.dto.PatientInfoDTO;
import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import com.mbackpatient.microservicebackpatient.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
     * @return La liste des patients.
     */
    @GetMapping(value = "/list")
    public List<PatientModel> listPatient() {

        List<PatientModel> patients = (List<PatientModel>) patientService.getPatients();
        return patients;
    }

    /**
     * Récupère le formulaire de mise à jour pour un patient spécifique.
     *
     * @param id         L'identifiant du patient à mettre à jour.
     * @return Les détails du patient à mettre à jour.
     * @throws PatientNotFoundException si le patient n'est pas trouvé.
     */
    @GetMapping(value = "/updateForm/{id}")
    public Optional<PatientModel> updatePatientForm(@PathVariable String id) {
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
     * @return Le patient mis à jour.
     */
    @PostMapping(value = "/update/{id}")
    public PatientModel updatePatient(@RequestBody PatientModel updatedPatient) {
        return patientService.updatePatient(updatedPatient);
    }

    /**
     * Ajoute un nouveau patient.
     *
     * @param newPatient  Le nouveau patient à ajouter.
     * @return Le patient ajouté.
     */
    @PostMapping(value = "/add")
    public PatientModel addPatient(@RequestBody PatientModel newPatient) {
        return patientService.insertPatient(newPatient);
    }

    /**
     * Supprime un patient spécifique.
     *
     * @param id         L'identifiant du patient à supprimer.
     */
    @DeleteMapping(value = "/delete/{id}")
    public void deletePatient(@PathVariable String id) {
        logger.info("Suppression du patient ID: {}", id);
        patientService.deletePatient(id);
    }

    /**
     * Gère la requête GET pour obtenir des informations sur un patient spécifique dans le contexte du diabète.
     * Cet endpoint reçoit un identifiant unique de patient et renvoie un objet DTO contenant des informations
     * clés telles que la date de naissance et le genre du patient.
     *
     * @param id L'identifiant unique du patient pour lequel les informations sont demandées.
     * @return PatientInfoDTO Un objet de transfert de données contenant la date de naissance et le genre du patient.
     */
    @GetMapping(value = "/getInfoDiabete/{id}")
    public PatientInfoDTO getInfoDiabete(@PathVariable String id) {
        logger.info("Requête reçue pour obtenir des informations sur le diabète pour le patient avec l'ID: {}", id);
        Optional<PatientModel> patientOpt= updatePatientForm(id);
        if (!patientOpt.isPresent()) {
            logger.error("Aucun patient trouvé avec l'ID: {}", id);
            return null;
        }
        PatientModel patient = patientOpt.get();
        PatientInfoDTO patientInfoDTO = new PatientInfoDTO();
        patientInfoDTO.setDateDeNaissance(patient.getDateDeNaissance());
        patientInfoDTO.setGenre((patient.getGenre()));
        logger.info("Informations sur le patient avec l'ID: {} récupérées avec succès", id);
        return patientInfoDTO;
    }
}