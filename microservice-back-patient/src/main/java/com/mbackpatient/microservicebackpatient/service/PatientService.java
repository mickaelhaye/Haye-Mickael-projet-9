/**
 * Package pour les classes de service définissant les fonctionnalités
 * liées au microservice back-patient.
 */
package com.mbackpatient.microservicebackpatient.service;

import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;

import java.util.Optional;

/**
 * Interface de service pour les opérations liées à {@link PatientModel}.
 * Elle déclare les méthodes nécessaires pour la gestion des patients.
 *
 * @author mickael hayé
 * @version 1.0
 */
public interface PatientService {

    /**
     * Récupère tous les patients enregistrés.
     *
     * @return une collection iterable de tous les patients
     */
    Iterable<PatientModel> getPatients();

    /**
     * Récupère un patient spécifique par son identifiant.
     *
     * @param id l'identifiant du patient à récupérer
     * @return un Optional contenant le patient s'il est trouvé, sinon Optional vide
     */
    Optional<PatientModel> getPatientById(String id);

    /**
     * Ajoute un patient.
     *
     * @param patient le modèle du patient à ajouter
     * @return le patient ajouté
     */
    PatientModel savePatient(PatientModel patient);


    /**
     * Supprime un patient par son identifiant.
     *
     * @param id l'identifiant du patient à supprimer
     */
    void deletePatient(String id);
}