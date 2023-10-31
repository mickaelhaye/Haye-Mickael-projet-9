package com.mbackpatient.microservicebackpatient.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

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
@Entity
@DynamicUpdate
@Table(name = "patient")
public class PatientModel {

    /**
     * Identifiant unique du patient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int patientId;

    /**
     * Nom de famille du patient.
     */
    @Column(name = "nom")
    private String nom;

    /**
     * Prénom du patient.
     */
    @Column(name = "prenom")
    private String prenom;

    /**
     * Date de naissance du patient, stockée sous forme de chaîne.
     */
    @Column(name = "date_de_naissance")
    private String dateDeNaissance;

    /**
     * Genre du patient.
     */
    @Column(name = "genre")
    private String genre;

    /**
     * Adresse du patient.
     */
    @Column(name = "adresse")
    private String adresse;

    /**
     * Numéro de téléphone du patient.
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * Constructeur utilisé pour créer une instance de PatientModel avec les détails spécifiques du patient.
     *
     * @param nom             Nom de famille du patient.
     * @param prenom          Prénom du patient.
     * @param dateDeNaissance Date de naissance du patient.
     * @param genre           Genre du patient.
     * @param adresse         Adresse du patient.
     * @param telephone       Numéro de téléphone du patient.
     */
    public PatientModel(String nom, String prenom, String dateDeNaissance, String genre, String adresse, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.genre = genre;
        this.adresse = adresse;
        this.telephone = telephone;
    }
}
