package com.mbackdiabete.microservicebackdiabete.controllerTest;

import com.mbackdiabete.microservicebackdiabete.model.dto.PatientInfoDTO;
import com.mbackdiabete.microservicebackdiabete.service.CalculService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de test pour DiabeteController.
 * Utilise les outils de test de Spring Boot pour réaliser des tests d'intégration.
 *
 * @author Mickael Hayé
 * @version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
public class DiabeteControllerTest {
    /**
     * Un utilisateur de test pour les besoins d'authentification.
     */
    private final SecurityProperties.User userTest = new SecurityProperties.User();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CalculService calculService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        userTest.setName("user1");
    }

    /**
     * Teste la méthode 'risqueDiabete' du contrôleur.
     * Simule les appels RestTemplate et vérifie la réponse du contrôleur.
     *
     * @throws Exception si le test échoue
     */
    @Test
    public void risqueDiabete() throws Exception {
        //Mock de restTemplate.exchange(url, HttpMethod.GET, entity, PatientInfoDTO.class);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -31);
        Date dateNaissance = calendar.getTime();

        PatientInfoDTO patientInfoDTO = new PatientInfoDTO();
        patientInfoDTO.setDateDeNaissance(dateNaissance);
        patientInfoDTO.setGenre("H");
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(PatientInfoDTO.class))).thenReturn(new ResponseEntity<>(patientInfoDTO, HttpStatus.OK));

        //Mock de restTemplate.exchange(url, HttpMethod.GET, entity, String[].class);
        String[] experesponseNotes = {"anormal", "fumeur", "poids"};
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String[].class))).thenReturn(new ResponseEntity<>(experesponseNotes, HttpStatus.OK));


        mockMvc.perform(get("/diabeteBack/risque/1234").with(user("user1")).header("Authorization", "")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(containsString("Borderline"))).andDo(print());

    }
}