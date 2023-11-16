/**
 * Package contenant les modèles utilisés par le microservice front.
 */
package com.mfront.microservicefront.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    private String id;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;

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