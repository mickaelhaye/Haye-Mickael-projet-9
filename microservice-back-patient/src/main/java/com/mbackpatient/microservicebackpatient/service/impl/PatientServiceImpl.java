/**
 * Package pour les classes de service implémentant les fonctionnalités spécifiques
 * au microservice back-patient.
 */
package com.mbackpatient.microservicebackpatient.service.impl;

import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import com.mbackpatient.microservicebackpatient.repository.PatientRepository;
import com.mbackpatient.microservicebackpatient.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Implémentation du service PatientService.
 * Cette classe gère les opérations CRUD pour les objets PatientModel, en interaction avec PatientRepository.
 * Elle fournit des méthodes pour récupérer, insérer, mettre à jour, et supprimer des patients.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Récupère tous les patients enregistrés dans la base de données.
     *
     * @return Iterable de PatientModel représentant tous les patients.
     */
    @Override
    public Iterable<PatientModel> getPatients() {
        logger.info("Récupération de la liste complète des patients.");
        return patientRepository.findAll();
    }

    /**
     * Récupère un patient spécifique par son identifiant.
     *
     * @param id L'identifiant unique du patient.
     * @return Optional contenant le PatientModel, s'il est trouvé.
     */
    @Override
    public Optional<PatientModel> getPatientById(String id) {
        logger.info("Récupération du patient avec l'ID : {}", id);
        return patientRepository.findById(Integer.parseInt(id));
    }

    /**
     * Insère un nouveau patient dans la base de données.
     *
     * @param patient L'objet PatientModel à insérer.
     * @return Le PatientModel inséré.
     */
    @Override
    public PatientModel savePatient(PatientModel patient) {
        logger.info("Ajout ou Update d'un nouveau patient : {}", patient.toString());
        return patientRepository.save(patient);
    }


    /**
     * Supprime un patient de la base de données par son identifiant.
     *
     * @param id L'identifiant du patient à supprimer.
     */
    @Override
    public void deletePatient(String id) {
        logger.info("Suppression du patient avec l'ID : {}", id);
        patientRepository.deleteById(Integer.parseInt(id));
    }
}