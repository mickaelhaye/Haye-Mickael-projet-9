package com.mbacknote.microservicebacknote.service;

import com.mbacknote.microservicebacknote.model.entity.NoteModel;
import java.util.List;
import java.util.Optional;

/**
 * Interface définissant les services pour la gestion des notes.
 * Cette interface spécifie les méthodes pour manipuler les données des notes.
 * @author mickael hayé
 * @version 1.0
 */
public interface NoteService {

    /**
     * Récupère les notes associées à un identifiant de patient spécifique.
     *
     * @param idPatient L'identifiant du patient pour lequel les notes sont recherchées.
     * @return Une liste de NoteModel correspondant au patient spécifié.
     */
    List<NoteModel> getNoteByIdPatient(String idPatient);

    /**
     * Récupère une note spécifique par son identifiant.
     *
     * @param id L'identifiant unique de la note.
     * @return Un Optional de NoteModel, pouvant être vide si aucune note n'est trouvée.
     */
    Optional<NoteModel> getNoteById(String id);

    /**
     * Insère une nouvelle note dans le système.
     *
     * @param insertNote L'objet NoteModel représentant la note à insérer.
     * @return Le NoteModel inséré.
     */
    NoteModel insertNote(NoteModel insertNote);

    /**
     * Met à jour une note existante.
     *
     * @param updatedNote L'objet NoteModel à mettre à jour.
     * @return Le NoteModel mis à jour.
     */
    NoteModel updateNote(NoteModel updatedNote);

    /**
     * Supprime une note spécifique à partir de son identifiant.
     *
     * @param id L'identifiant de la note à supprimer.
     */
    void deleteNote(String id);

    /**
     * Supprime toutes les notes associées à un identifiant de patient donné.
     *
     * @param patientId L'identifiant du patient dont les notes doivent être supprimées.
     */
    void deleteNoteByPatientId(String patientId);

}