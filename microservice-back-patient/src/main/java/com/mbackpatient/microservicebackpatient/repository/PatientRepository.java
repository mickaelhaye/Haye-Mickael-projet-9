/**
 * Package pour les classes de dépôt associées au microservice back-patient.
 */
package com.mbackpatient.microservicebackpatient.repository;

import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface de dépôt pour {@link PatientModel}.
 * Ce dépôt fournit des opérations CRUD pour les entités {@link PatientModel}.
 * Elle étend l'interface {@link CrudRepository} de Spring Data JPA, qui offre
 * des opérations CRUD génériques sur un dépôt pour un type spécifique.
 *
 * @author mickael hayé
 * @version 1.0
 */
public interface PatientRepository extends CrudRepository<PatientModel, Integer> {
}