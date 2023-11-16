package com.mbacknote.microservicebacknote.service.impl;

import com.mbacknote.microservicebacknote.model.entity.NoteModel;
import com.mbacknote.microservicebacknote.repository.NoteRepository;
import com.mbacknote.microservicebacknote.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service d'implémentation pour la gestion des notes dans le microservice BackNote.
 * Cette classe fournit des méthodes pour opérer sur les objets de type NoteModel.
 * @author mickael hayé
 * @version 1.0
 */
@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    private NoteRepository noteRepository;

    /**
     * Récupère une liste de notes basée sur l'identifiant du patient.
     *
     * @param idPatient L'identifiant du patient.
     * @return Liste des NoteModel correspondantes à l'identifiant du patient.
     */
    @Override
    public List<NoteModel> getNoteByIdPatient(String idPatient) {
        logger.info("getNoteByIdPatient pour l'ID patient: {}", idPatient);
        return noteRepository.findByIdPatient(idPatient);
    }

    /**
     * Obtient une note spécifique par son identifiant.
     *
     * @param id L'identifiant de la note.
     * @return Un Optional de NoteModel.
     */
    @Override
    public Optional<NoteModel> getNoteById(String id) {
        logger.info("getNoteById pour l'ID de la note: {}", id);
        return noteRepository.findById(id);
    }

    /**
     * Insère une nouvelle note dans la base de données.
     *
     * @param insertNote L'objet NoteModel à insérer.
     * @return Le NoteModel inséré.
     */
    @Override
    public NoteModel insertNote(NoteModel insertNote) {
        logger.info("insertNote");
        return noteRepository.insert(insertNote);
    }

    /**
     * Met à jour une note existante.
     *
     * @param updatedNote L'objet NoteModel mis à jour.
     * @return Le NoteModel mis à jour.
     */
    @Override
    public NoteModel updateNote(NoteModel updatedNote) {
        logger.info("updateNote pour la note ID: {}", updatedNote.getId());
        return noteRepository.save(updatedNote);
    }

    /**
     * Supprime une note de la base de données en utilisant son identifiant.
     *
     * @param id L'identifiant de la note à supprimer.
     */
    @Override
    public void deleteNote(String id) {
        logger.info("deleteNote pour l'ID de la note: {}", id);
        noteRepository.deleteById(id);
    }

    /**
     * Supprime toutes les notes associées à un identifiant de patient donné.
     *
     * @param idPatient L'identifiant du patient dont les notes doivent être supprimées.
     */
    @Override
    public void deleteNoteByPatientId(String idPatient) {
        logger.info("deleteNoteByPatientId pour l'ID patient: {}", idPatient);
        noteRepository.deleteByIdPatient(idPatient);
    }
}