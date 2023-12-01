package com.mbackpatient.microservicebackpatient.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import com.mbackpatient.microservicebackpatient.service.PatientService;
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
 * Classe de tests pour PatientController.
 * Utilise SpringBootTest pour charger le contexte complet de l'application,
 * et AutoConfigureMockMvc pour configurer MockMvc pour les tests de contrôleur.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    private final SecurityProperties.User userTest = new SecurityProperties.User();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Utilisé pour convertir des objets en chaînes JSON

    @Autowired
    private PatientService patientService;

    /**
     * Configuration initiale avant chaque test.
     */
    @BeforeEach
    public void setup() {
        userTest.setName("user1");
    }

    /**
     * Teste la récupération de la liste des patients.
     * Vérifie que la réponse est OK et que la taille de la liste est correcte.
     */
    @Test
    public void listPatientTest() throws Exception {
        mockMvc.perform(get("/patientBack/list").with(user("user1"))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)));
    }

    /**
     * Teste la récupération du formulaire de mise à jour d'un patient.
     * Vérifie le succès ou l'échec de la récupération en fonction de l'ID du patient.
     */
    @Test
    public void updatePatientFormTest() throws Exception {
        int idPatient = 1;
        mockMvc.perform(get("/patientBack/updateForm/" + idPatient).with(user("user1"))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.nom", is("TestNone")));

        idPatient = 999;
        mockMvc.perform(get("/patientBack/updateForm/" + idPatient).with(user("user1"))).andDo(print()).andExpect(status().isNotFound());
    }

    /**
     * Teste la mise à jour d'un patient.
     * Vérifie que la mise à jour est correctement effectuée et que les données sont mises à jour.
     */
    @Test
    public void updatePatientTest() throws Exception {
        int patientId = 4;
        PatientModel updatedPatient = new PatientModel();
        updatedPatient.setId(patientId);
        updatedPatient.setNom("TestNom");

        // Exécution du test
        mockMvc.perform(post("/patientBack/update/" + patientId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedPatient)).with(user("user1"))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.nom", is("TestNom")));

        // Exécution du test avec changement de la note pour un autre test
        updatedPatient.setNom("TestNom2");
        mockMvc.perform(post("/patientBack/update/" + patientId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedPatient)).with(user("user1"))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.nom", is("TestNom2")));

    }

    /**
     * Teste l'ajout et la suppression d'un patient.
     * Vérifie que le patient est correctement ajouté puis supprimé du système.
     */
    @Test
    public void addAndDeletePatientTest() throws Exception {
        // Création d'un nouveau patient
        PatientModel newPatient = new PatientModel();
        newPatient.setNom("TestNomAdd");

        // Exécution de la requête POST pour ajouter la nouvelle note
        MvcResult result = mockMvc.perform(post("/patientBack/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newPatient)).with(user("user1"))).andExpect(status().isOk()).andExpect(jsonPath("$.nom", is("TestNomAdd"))).andReturn();

        // Extraction de l'ID de la note ajoutée
        String jsonResponse = result.getResponse().getContentAsString();
        PatientModel addedPatient = objectMapper.readValue(jsonResponse, PatientModel.class);
        int patientId = addedPatient.getId();

        // Suppression de la note
        mockMvc.perform(delete("/patientBack/delete/" + patientId).with(user("user1"))).andExpect(status().isOk());
    }

    /**
     * Teste la récupération d'informations spécifiques (ici, liées au diabète) d'un patient.
     * Vérifie que les informations sont correctement retournées ou que l'erreur appropriée est renvoyée.
     */
    @Test
    public void getInfoDiabeteTest() throws Exception {

        String patientId = "1";
        mockMvc.perform(get("/patientBack/getInfoDiabete/" + patientId).with(user("user1"))).andExpect(status().isOk()).andExpect(jsonPath("$.genre").value("F"));
        patientId = "999";
        mockMvc.perform(get("/patientBack/getInfoDiabete/" + patientId).with(user("user1"))).andExpect(status().isNotFound());

    }
}