package com.mbacknote.microservicebacknote.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbacknote.microservicebacknote.model.entity.NoteModel;
import com.mbacknote.microservicebacknote.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de test pour le NoteController.
 * Elle contient des méthodes pour tester les différentes fonctionnalités du contrôleur de notes.
 * @author mickael hayé
 * @version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    private final SecurityProperties.User userTest = new SecurityProperties.User();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Utilisé pour convertir des objets en chaînes JSON

    @Autowired
    private NoteService noteService;

    /**
     * Configuration initiale avant chaque test.
     */
    @BeforeEach
    public void setup() {
        userTest.setName("user1");
    }

    /**
     * Teste la récupération de la liste des notes pour un patient donné.
     * Vérifie que la requête GET renvoie le statut OK et que les données retournées sont correctes.
     */
    @Test
    public void listNoteTest() throws Exception {
        String idPatient = "4";
        mockMvc.perform(get("/noteBack/list/" + idPatient).with(user("user1"))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0].idPatient", is(idPatient))).andExpect(jsonPath("$[0].note", is("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments")));
    }

    /**
     * Teste la récupération des détails d'une note pour mise à jour.
     * Vérifie que la requête GET renvoie le statut OK pour une note existante et le statut NotFound pour une note inexistante.
     */
    @Test
    public void updateNoteFormTest() throws Exception {
        String idNote = "6554cad78d4ac35957755442";
        mockMvc.perform(get("/noteBack/updateForm/" + idNote).with(user("user1"))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is("6554cad78d4ac35957755442"))).andExpect(jsonPath("$.idPatient", is("1"))).andExpect(jsonPath("$.note", is("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé")));

        idNote = "6554cad78d4ac35";
        mockMvc.perform(get("/noteBack/updateForm/" + idNote).with(user("user1"))).andDo(print()).andExpect(status().isNotFound());
    }

    /**
     * Teste la mise à jour d'une note.
     * Vérifie que la requête POST renvoie le statut OK et que les données de la note sont mises à jour correctement.
     */
    @Test
    public void updateNoteTest() throws Exception {
        String noteId = "6554cb8c8d4ac3595775544a";
        NoteModel updatedNote = new NoteModel();
        updatedNote.setId("6554cb8c8d4ac3595775544a");
        updatedNote.setIdPatient("patientId");
        updatedNote.setNote("Note mise à jour");

        // Exécution du test
        mockMvc.perform(post("/noteBack/update/" + noteId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedNote)).with(user("user1"))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(noteId))).andExpect(jsonPath("$.note", is("Note mise à jour")));

        // Exécution du test avec changement de la note pour un autre test
        updatedNote.setNote("Note mise à jour2");
        mockMvc.perform(post("/noteBack/update/" + noteId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedNote)).with(user("user1"))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(noteId))).andExpect(jsonPath("$.note", is("Note mise à jour2")));

    }

    /**
     * Teste l'ajout d'une nouvelle note et sa suppression immédiate.
     * Vérifie que les requêtes POST et DELETE renvoient le statut OK.
     */
    @Test
    public void addAndDeleteNoteTest() throws Exception {
        // Création d'une nouvelle note
        String idPatient = "6554caa1e5d78f1ae6f39f4b";
        NoteModel newNote = new NoteModel();
        newNote.setIdPatient(idPatient);
        newNote.setNote("Contenu de la nouvelle note");

        // Exécution de la requête POST pour ajouter la nouvelle note
        MvcResult result = mockMvc.perform(post("/noteBack/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newNote)).with(user("user1"))).andExpect(status().isOk()).andExpect(jsonPath("$.idPatient", is(idPatient))).andExpect(jsonPath("$.note", is("Contenu de la nouvelle note"))).andReturn();

        // Extraction de l'ID de la note ajoutée
        String jsonResponse = result.getResponse().getContentAsString();
        NoteModel addedNote = objectMapper.readValue(jsonResponse, NoteModel.class);
        String noteId = addedNote.getId();

        // Suppression de la note
        mockMvc.perform(delete("/noteBack/delete/" + noteId).with(user("user1"))).andExpect(status().isOk());
    }

    /**
     * Teste la suppression de toutes les notes associées à un identifiant de patient.
     * Vérifie que la requête DELETE renvoie le statut OK et que toutes les notes sont effectivement supprimées.
     */
    @Test
    public void deleteNoteByPatientIdTest() throws Exception {
        String patientId = "6554ca6de5d78f1ae6f39f4a";

        // Ajout de quelques notes pour le patient
        addNoteForPatient(patientId, "Note 1");
        addNoteForPatient(patientId, "Note 2");

        // Suppression de toutes les notes pour ce patient
        mockMvc.perform(delete("/noteBack/deleteAll/" + patientId).with(user("user1"))).andExpect(status().isOk());

        // Vérifiez que toutes les notes ont été supprimées
        mockMvc.perform(get("/noteBack/list/" + patientId).with(user("user1"))).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Méthode utilitaire pour ajouter une note à un patient.
     * @param patientId L'identifiant du patient.
     * @param noteContent Le contenu de la note.
     */
    private void addNoteForPatient(String patientId, String noteContent) throws Exception {
        NoteModel newNote = new NoteModel();
        newNote.setIdPatient(patientId);
        newNote.setNote(noteContent);

        mockMvc.perform(post("/noteBack/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newNote)));
    }

    /**
     * Teste la récupération de la liste des notes sous forme de chaînes de caractères pour un patient spécifique.
     * Vérifie que la requête GET renvoie le statut OK et que la liste des notes est correcte.
     */
    @Test
    public void getListeNotesTest() throws Exception {
        String patientId = "4";

        // Exécution de la requête GET pour récupérer la liste des notes
        mockMvc.perform(get("/noteBack/getListeNotes/" + patientId).with(user("user1"))).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3))).andExpect(jsonPath("$[0]", is("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments")));
    }
}