package com.mfront.microservicefront.controller;

import com.mfront.microservicefront.model.NoteModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe de test pour NoteController.
 * Teste différentes méthodes du contrôleur pour s'assurer qu'elles répondent correctement aux requêtes HTTP.
 *
 * @author mickael hayé
 * @version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    private final SecurityProperties.User userTest = new SecurityProperties.User();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * Configure l'environnement de test avant chaque test.
     */
    @BeforeEach
    public void setup() {
        userTest.setName("user1");
    }

    /**
     * Teste la méthode listNote du NoteController.
     * Vérifie le comportement en cas de succès et en cas d'échec lors de la récupération des notes.
     */
    @Test
    public void listNoteTest() throws Exception {
        NoteModel note1 = new NoteModel();
        note1.setIdPatient("1");
        note1.setNote("note1");
        NoteModel note2 = new NoteModel();
        note2.setIdPatient("2");
        note2.setNote("note2");
        NoteModel[] notes = {note1, note2};
        //cas ok
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(NoteModel[].class))).thenReturn(new ResponseEntity<>(notes, HttpStatus.OK));

        mockMvc.perform(get("/noteFront/list/1/test").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("notes")).andExpect(model().attribute("nomPatient", "test")).andExpect(model().attribute("idPatient", "1")).andExpect(view().name("note/list")).andExpect(MockMvcResultMatchers.content().string(containsString("note1"))).andDo(print());

        //cas Nok
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(NoteModel[].class))).thenThrow(new RestClientException("Failed to connect"));

        mockMvc.perform(get("/noteFront/list/1/test").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service de notes.")).andExpect(view().name("error/errorPage")).andDo(print());

    }

    /**
     * Teste la méthode updateNoteForm du NoteController.
     * Vérifie que le formulaire de mise à jour est correctement retourné.
     * Teste également le comportement en cas d'erreur lors de la connexion au service de notes.
     */
    @Test
    public void updateNoteFormTest() throws Exception {
        String id = "1";
        NoteModel noteModel = new NoteModel();
        noteModel.setNote("note test");
        noteModel.setIdPatient("1");
        noteModel.setId("1");
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(NoteModel.class))).thenReturn(new ResponseEntity<>(noteModel, HttpStatus.OK));

        //cas ok
        mockMvc.perform(get("/noteFront/updateForm/1").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("note")).andExpect(view().name("note/update")).andDo(print());

        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(NoteModel.class))).thenThrow(new RestClientException("Failed to connect"));

        //cas Nok
        mockMvc.perform(get("/noteFront/updateForm/1").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service de notes.")).andExpect(view().name("error/errorPage")).andDo(print());
    }

    /**
     * Teste la méthode updateNote du NoteController.
     * Vérifie la redirection après la mise à jour d'une note et le comportement en cas d'erreur.
     */
    @Test
    public void updateNoteTest() throws Exception {
        String id = "1";
        NoteModel note = new NoteModel();
        note.setId("1");
        note.setIdPatient("1");
        note.setNote("Note test");

        //cas ok
        mockMvc.perform(post("/noteFront/update/1").with(user("user1")).header("Authorization", "").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("note", note.getNote()).param("id", note.getId())).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("http://localhost:9000/noteFront/list/null/null")).andDo(print());
        //cas Nok
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(Void.class))).thenThrow(new RestClientException("Failed to connect"));

        mockMvc.perform(post("/noteFront/update/1").with(user("user1")).header("Authorization", "").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("note", note.getNote()).param("id", note.getId())).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service de notes.")).andExpect(view().name("error/errorPage")).andDo(print());
    }

    /**
     * Teste la méthode addNoteForm du NoteController.
     * Vérifie que le formulaire d'ajout de note est correctement retourné.
     */
    @Test
    public void addNoteFormTest() throws Exception {
        NoteModel note = new NoteModel();
        note.setId("1");
        note.setIdPatient("1");
        note.setNote("Note test");
        mockMvc.perform(get("/noteFront/add").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("note")).andExpect(view().name("note/add")).andDo(print());
    }

    /**
     * Teste la méthode addNote du NoteController.
     * Vérifie la redirection après l'ajout d'une nouvelle note et le comportement en cas d'erreur.
     */
    @Test
    public void addNoteTest() throws Exception {
        NoteModel note = new NoteModel();
        note.setId("1");
        note.setIdPatient("1");
        note.setNote("Note test");
        //cas ok
        mockMvc.perform(post("/noteFront/add").with(user("user1")).header("Authorization", "").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("note", note.getNote())).andExpect(status().is3xxRedirection()).andDo(print());
        //cas Nok
        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(Void.class))).thenThrow(new RestClientException("Failed to connect"));

        mockMvc.perform(post("/noteFront/add").with(user("user1")).header("Authorization", "").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("note", note.getNote())).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service de notes.")).andExpect(view().name("error/errorPage")).andDo(print());
    }

    /**
     * Teste la méthode deleteNote du NoteController.
     * Vérifie la redirection après la suppression d'une note et le comportement en cas d'erreur.
     */
    @Test
    public void deleteNoteTest() throws Exception {
        //cas ok
        mockMvc.perform(get("/noteFront/delete/1").with(user("user1")).header("Authorization", "")).andExpect(status().is3xxRedirection()).andDo(print());

        //cas Nok
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class))).thenThrow(new RestClientException("Failed to connect"));
        mockMvc.perform(get("/noteFront/delete/1").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service de notes.")).andDo(print());
    }
}