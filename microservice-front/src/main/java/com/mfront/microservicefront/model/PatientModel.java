package com.mfront.microservicefront.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientModel {
    private int patientId;

    private String nom;

    private String prenom;

    private String dateDeNaissance;

    private String genre;

    private String adresse;

    private String telephone;
}
