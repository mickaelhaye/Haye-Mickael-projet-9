package com.mfront.microservicefront.controller;

import com.mfront.microservicefront.model.PatientModel;
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
 * Classe de test pour PatientController.
 * Fournit des tests d'intégration pour les différents endpoints du PatientController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

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
     * Teste le endpoint '/patientFront/list' pour vérifier la récupération et l'affichage de la liste des patients.
     * Simule deux cas : un cas réussi et un cas où une exception est levée.
     */
    @Test
    public void listPatientTest() throws Exception {
        PatientModel patient1 = new PatientModel();
        patient1.setNom("patientTest1");

        PatientModel patient2 = new PatientModel();
        patient2.setNom("patientTest2");

        PatientModel[] patients = {patient1, patient2};
        //cas ok
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(PatientModel[].class))).thenReturn(new ResponseEntity<>(patients, HttpStatus.OK));

        mockMvc.perform(get("/patientFront/list").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("patients")).andExpect(view().name("patient/list")).andExpect(MockMvcResultMatchers.content().string(containsString("patientTest1"))).andDo(print());

        //cas Nok
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(PatientModel[].class))).thenThrow(new RestClientException("Failed to connect"));

        mockMvc.perform(get("/patientFront/list").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service de patients.")).andExpect(view().name("error/errorPage")).andDo(print());

    }

    /**
     * Teste le endpoint '/patientFront/updateForm/{id}' pour vérifier l'affichage du formulaire de mise à jour.
     * Simule deux cas : un cas réussi et un cas où une exception est levée.
     */
    @Test
    public void updatePatientFormTest() throws Exception {
        String id = "1";
        PatientModel patientModel = new PatientModel();
        patientModel.setNom("patientTest1");
        patientModel.setId("1");

        //cas ok
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(PatientModel.class))).thenReturn(new ResponseEntity<>(patientModel, HttpStatus.OK));
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(new ResponseEntity<>("Rapport de diabète", HttpStatus.OK));
        mockMvc.perform(get("/patientFront/updateForm/1").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("patient")).andExpect(view().name("patient/update")).andDo(print());

        //cas Nok
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(PatientModel.class))).thenThrow(new RestClientException("Failed to connect"));
        mockMvc.perform(get("/patientFront/updateForm/1").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service de patients.")).andExpect(view().name("error/errorPage")).andDo(print());
    }

    /**
     * Teste le endpoint '/patientFront/update/{id}' pour vérifier la mise à jour d'un patient.
     * Simule deux cas : un cas réussi et un cas où une exception est levée lors de la mise à jour.
     */
    @Test
    public void updatePatientTest() throws Exception {
        String id = "1";
        PatientModel patient = new PatientModel();
        patient.setId("1");
        patient.setNom("patientTest1");

        //cas ok
        mockMvc.perform(post("/patientFront/update/1").with(user("user1")).header("Authorization", "").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("patient", patient.getNom()).param("id", patient.getId())).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("http://localhost:9000/patientFront/list")).andDo(print());
        //cas Nok
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), eq(Void.class))).thenThrow(new RestClientException("Failed to connect"));

        mockMvc.perform(post("/patientFront/update/1").with(user("user1")).header("Authorization", "").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("patient", patient.getNom()).param("id", patient.getId())).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service patients.")).andExpect(view().name("error/errorPage")).andDo(print());
    }

    /**
     * Teste le endpoint '/patientFront/add' pour vérifier l'affichage du formulaire d'ajout d'un nouveau patient.
     */
    @Test
    public void addPatientFormTest() throws Exception {
        PatientModel patient = new PatientModel();
        patient.setId("1");
        patient.setNom("patientTest1");
        mockMvc.perform(get("/patientFront/add").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("patient")).andExpect(view().name("patient/add")).andDo(print());
    }

    /**
     * Teste le endpoint '/patientFront/add' pour vérifier l'ajout d'un nouveau patient.
     * Simule deux cas : un cas réussi et un cas où une exception est levée lors de l'ajout.
     */
    @Test
    public void addPatientTest() throws Exception {
        PatientModel patient = new PatientModel();
        patient.setId("1");
        patient.setNom("patientTest1");
        //cas ok
        mockMvc.perform(post("/patientFront/add").with(user("user1")).header("Authorization", "").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("patient", patient.getNom())).andExpect(status().is3xxRedirection()).andDo(print());
        //cas Nok
        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(Void.class))).thenThrow(new RestClientException("Failed to connect"));

        mockMvc.perform(post("/patientFront/add").with(user("user1")).header("Authorization", "").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("patient", patient.getNom())).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service patients.")).andExpect(view().name("error/errorPage")).andDo(print());
    }

    /**
     * Teste le endpoint '/patientFront/delete/{id}' pour vérifier la suppression d'un patient.
     * Simule deux cas : un cas réussi et un cas où une exception est levée lors de la suppression.
     */
    @Test
    public void deletePatientTest() throws Exception {
        //cas ok
        mockMvc.perform(get("/patientFront/delete/1").with(user("user1")).header("Authorization", "")).andExpect(status().is3xxRedirection()).andDo(print());

        //cas Nok
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Void.class))).thenThrow(new RestClientException("Failed to connect"));
        mockMvc.perform(get("/patientFront/delete/1").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(model().attributeExists("errorMessage")).andExpect(model().attribute("errorMessage", "Impossible de se connecter au service patients.")).andDo(print());
    }
}