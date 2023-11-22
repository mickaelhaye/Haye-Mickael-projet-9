package com.mbackdiabete.microservicebackdiabete.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Classe DTO (Data Transfer Object) représentant les informations d'un patient dans le cadre du microservice back-diabete.
 * Cette classe est utilisée pour transférer des données relatives aux patients, en particulier leur date de naissance et leur genre.
 * Utilise Lombok pour générer automatiquement les getters, setters, un constructeur avec tous les arguments et un constructeur sans arguments.
 *
 * @author Mickael Hayé
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientInfoDTO {

    /**
     * Date de naissance du patient.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;

    /**
     * Genre du patient.
     */
    private String genre;
}
