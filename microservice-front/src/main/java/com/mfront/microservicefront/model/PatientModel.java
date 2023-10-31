/**
 * Package contenant les modèles utilisés par le microservice front.
 */
package com.mfront.microservicefront.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modèle représentant un patient.
 * <p>
 * Cette classe contient les informations de base sur un patient,
 * y compris son identifiant, nom, prénom, date de naissance,
 * genre, adresse et numéro de téléphone.
 * </p>
 *
 * @author mickael hayé
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientModel {

    /**
     * L'identifiant unique du patient.
     */
    private int patientId;

    /**
     * Le nom du patient.
     */
    private String nom;

    /**
     * Le prénom du patient.
     */
    private String prenom;

    /**
     * La date de naissance du patient.
     */
    private String dateDeNaissance;

    /**
     * Le genre du patient.
     */
    private String genre;

    /**
     * L'adresse du patient.
     */
    private String adresse;

    /**
     * Le numéro de téléphone du patient.
     */
    private String telephone;
}
