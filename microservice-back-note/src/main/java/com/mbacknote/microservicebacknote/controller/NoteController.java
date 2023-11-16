package com.mbacknote.microservicebacknote.controller;

import com.mbacknote.microservicebacknote.exceptions.NoteNotFoundException;
import com.mbacknote.microservicebacknote.model.entity.NoteModel;
import com.mbacknote.microservicebacknote.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour la gestion des notes.
 *
 * @author mickael hayé
 * @version 1.0
 */
@RestController
@RequestMapping("noteBack")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    /**
     * Liste toutes les notes associées à un patient spécifique.
     *
     * @param idPatient l'identifiant du patient.
     * @return une liste des notes du patient.
     */
    @GetMapping(value = "/list/{idPatient}")
    public List<NoteModel> listNote(@PathVariable String idPatient) {
        return noteService.getNoteByIdPatient(idPatient);
    }

    /**
     * Récupère les détails d'une note pour mise à jour.
     *
     * @param id l'identifiant de la note.
     * @return une note spécifique ou lève une exception si elle n'est pas trouvée.
     * @throws NoteNotFoundException si la note n'est pas trouvée.
     */
    @GetMapping(value = "/updateForm/{id}")
    public Optional<NoteModel> updateNoteForm(@PathVariable String id) {
        Optional<NoteModel> note = noteService.getNoteById(id);
        if (!note.isPresent()) {
            logger.error("Note avec ID: {} non trouvé", id);
            throw new NoteNotFoundException("La note correspondante à l'id " + id + " n'existe pas");
        }
        return note;
    }

    /**
     * Met à jour une note spécifique.
     *
     * @param updatedNote la note mise à jour.
     * @return la note mise à jour.
     */
    @PostMapping(value = "/update/{id}")
    public NoteModel updateNote(@RequestBody NoteModel updatedNote) {
        return noteService.updateNote(updatedNote);
    }

    /**
     * Ajoute une nouvelle note.
     *
     * @param newNote la nouvelle note à ajouter.
     * @return la note nouvellement créée.
     */
    @PostMapping(value = "/add")
    public NoteModel addNote(@RequestBody NoteModel newNote) {
        return noteService.insertNote(newNote);
    }

    /**
     * Supprime une note spécifique.
     *
     * @param id l'identifiant de la note à supprimer.
     */
    @DeleteMapping(value = "/delete/{id}")
    public void deleteNote(@PathVariable String id) {
        logger.info("Suppression de la note ID: {}", id);
        noteService.deleteNote(id);
    }

    /**
     * Supprime toutes les notes associées à un patient spécifique.
     *
     * @param id l'identifiant du patient dont les notes doivent être supprimées.
     */
    @DeleteMapping(value = "/deleteAll/{id}")
    public void deleteNoteByPatientId(@PathVariable String id) {
        logger.info("Suppression des notes du patient ID: {}", id);
        noteService.deleteNoteByPatientId(id);
    }
}