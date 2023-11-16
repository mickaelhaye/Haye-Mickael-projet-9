/**
 * Package pour les classes de service implémentant les fonctionnalités spécifiques
 * au microservice back-patient.
 */
package com.mbackpatient.microservicebackpatient.service.impl;

import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import com.mbackpatient.microservicebackpatient.repository.PatientRepository;
import com.mbackpatient.microservicebackpatient.service.PatientService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implémentation du service pour les opérations liées à {@link PatientModel}.
 * Cette classe met en œuvre les méthodes définies dans l'interface {@link PatientService}
 * pour interagir avec le dépôt de données de patients.
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
     * {@inheritDoc}
     */
    @Override
    public Iterable<PatientModel> getPatients() {
        logger.info("Récupération de la liste complète des patients.");
        return patientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PatientModel> getPatientById(String id) {
        logger.info("Récupération du patient avec l'ID : {}", id);
        return patientRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientModel insertPatient(PatientModel insertPatient) {
        logger.info("Ajout d'un nouveau patient : {}", insertPatient.toString());
        return patientRepository.insert(insertPatient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientModel updatePatient(PatientModel updatedPatient) {
        logger.info("Mis à jour d'un patient : {}", updatedPatient.toString());
        return patientRepository.save(updatedPatient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePatient(String id) {
        logger.info("Suppression du patient avec l'ID : {}", id);
        patientRepository.deleteById(id);
    }
}