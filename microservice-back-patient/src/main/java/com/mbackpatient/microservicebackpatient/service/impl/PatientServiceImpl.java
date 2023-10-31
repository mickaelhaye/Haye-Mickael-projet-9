/**
 * Package pour les classes de service implémentant les fonctionnalités spécifiques
 * au microservice back-patient.
 */
package com.mbackpatient.microservicebackpatient.service.impl;

import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import com.mbackpatient.microservicebackpatient.repository.PatientRepository;
import com.mbackpatient.microservicebackpatient.service.PatientService;
import jakarta.annotation.PostConstruct;
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

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Méthode d'initialisation pour charger la base de données avec des données de test.
     * TODO : Cette méthode est temporaire et doit être supprimée dans la version finale.
     */
    //todo à enlever
    @PostConstruct
    public void chargementBdd(){
        patientRepository.save(new PatientModel("TestNone","Test","1966-12-31","F","1 Brookside St", "100-222-3333"));
        patientRepository.save(new PatientModel("TestBorderline","Test","1945-06-24","M","2 High St", "200-333-4444"));
        patientRepository.save(new PatientModel("TestInDanger","Test","2004-06-18","M","3 Club Road", "300-444-5555"));
        patientRepository.save(new PatientModel("TestEarlyOnset","Test","2002-06-28","F","4 Valley Dr", "400-555-6666"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<PatientModel> getPatients() {
        return patientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PatientModel> getPatientById(Integer id) {
        return patientRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientModel addPatient(PatientModel updatedPatient) {
        return patientRepository.save(updatedPatient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }
}
