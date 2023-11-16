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
public class NoteModel {

    /**
     * L'identifiant unique de la note.
     */
    private String id;

    private String idPatient;

    private String nomPatient;

    private String note;
}