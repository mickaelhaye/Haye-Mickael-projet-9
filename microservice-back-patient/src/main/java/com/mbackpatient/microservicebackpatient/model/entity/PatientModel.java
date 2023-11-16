package com.mbackpatient.microservicebackpatient.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Classe représentant un patient dans le système de gestion de patients.
 * Utilise Lombok pour générer automatiquement les getters, setters, et le constructeur.
 * Cette classe est mappée à une table nommée 'patient' dans la base de données.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "patients")
public class PatientModel {

    /**
     * Identifiant unique du patient.
     */
    @Id
    private String id;

    /**
     * Nom de famille du patient.
     */
    @Field("nom")
    private String nom;

    /**
     * Prénom du patient.
     */
    @Field("prenom")
    private String prenom;

    /**
     * Date de naissance du patient, stockée sous forme de chaîne.
     */
    @Field("date_de_naissance")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;

    /**
     * Genre du patient.
     */
    @Field("genre")
    private String genre;

    /**
     * Adresse du patient.
     */
    @Field("adresse")
    private String adresse;

    /**
     * Numéro de téléphone du patient.
     */
    @Field("telephone")
    private String telephone;

}