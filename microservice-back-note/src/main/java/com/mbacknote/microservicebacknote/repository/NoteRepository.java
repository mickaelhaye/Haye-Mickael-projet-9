package com.mbacknote.microservicebacknote.repository;

import com.mbacknote.microservicebacknote.model.entity.NoteModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface de dépôt pour les opérations CRUD sur les notes dans MongoDB.
 * Étend MongoRepository pour fournir des opérations de base de données.
 * @author mickael hayé
 * @version 1.0
 */
public interface NoteRepository extends MongoRepository<NoteModel, String> {

    /**
     * Trouve toutes les notes associées à un patient spécifique.
     *
     * @param idPatient l'identifiant du patient.
     * @return une liste de NoteModel correspondant au patient spécifié.
     */
    List<NoteModel> findByIdPatient(String idPatient);

    /**
     * Supprime toutes les notes associées à un patient spécifique.
     *
     * @param idPatient l'identifiant du patient dont les notes doivent être supprimées.
     */
    void deleteByIdPatient(String idPatient);
}