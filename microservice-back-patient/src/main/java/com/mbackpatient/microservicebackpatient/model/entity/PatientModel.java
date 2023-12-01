package com.mbackpatient.microservicebackpatient.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Id;
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
@Entity
@DynamicUpdate
@Table(name = "patients")
public class PatientModel {

    /**
     * Identifiant unique du patient.
     */
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private int id;

    /**
     * Nom de famille du patient.
     */
    @Column(name ="nom")
    private String nom;

    /**
     * Prénom du patient.
     */
    @Column(name ="prenom")
    private String prenom;

    /**
     * Date de naissance du patient, stockée sous forme de chaîne.
     */
    @Column(name ="date_de_naissance")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;

    /**
     * Genre du patient.
     */
    @Column(name ="genre")
    private String genre;

    /**
     * Adresse du patient.
     */
    @Column(name ="adresse")
    private String adresse;

    /**
     * Numéro de téléphone du patient.
     */
    @Column(name ="telephone")
    private String telephone;

}