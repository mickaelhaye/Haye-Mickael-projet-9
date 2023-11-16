package com.mbacknote.microservicebacknote.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Modèle représentant une note dans l'application.
 * Mappé à la collection 'notes' dans MongoDB.
 * @author mickael hayé
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class NoteModel {

    /**
     * Identifiant unique de la note.
     */
    @Id
    private String id;

    /**
     * Identifiant du patient associé à la note.
     * Stocké dans le champ 'patid' de la base de données.
     */
    @Field("patid")
    private String idPatient;

    /**
     * Nom du patient associé à la note.
     * Stocké dans le champ 'patient'.
     */
    @Field("patient")
    private String nomPatient;

    /**
     * Contenu de la note.
     * Stocké dans le champ 'note'.
     */
    @Field("note")
    private String note;

    /**
     * Constructeur personnalisé pour créer une note avec des détails spécifiques.
     *
     * @param idPatient Identifiant du patient associé à la note.
     * @param nomPatient Nom du patient.
     * @param note Contenu de la note.
     */
    public NoteModel(String idPatient, String nomPatient, String note) {
        this.idPatient = idPatient;
        this.nomPatient = nomPatient;
        this.note = note;
    }
}