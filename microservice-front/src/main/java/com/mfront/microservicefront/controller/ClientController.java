package com.mfront.microservicefront.controller;

import com.mfront.microservicefront.configuration.CustomProperties;
import com.mfront.microservicefront.model.PatientModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("patientFront")
public class ClientController {
    private final RestTemplate restTemplate;
    private final CustomProperties prop;

    @Autowired
    public ClientController(RestTemplate restTemplate, CustomProperties prop) {
        this.restTemplate = restTemplate;
        this.prop = prop;
    }

    @GetMapping("/list")
    public String listPatient(Model model, @RequestHeader("Authorization") String authHeader) {
        String url = prop.getGatewayPath() + "/patientBack/list";

        // Configuration des en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // Effectuer la requête avec les en-têtes
        ResponseEntity<PatientModel[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, PatientModel[].class);
        PatientModel[] patientsArray = response.getBody();

        List<PatientModel> patients = Arrays.asList(patientsArray);
        model.addAttribute("patients", patients);

        return "patient/list";
    }

    @GetMapping("/updateForm/{id}")
    public String updatePatientForm(@PathVariable int id, Model model, @RequestHeader("Authorization") String authHeader) {
        String url = prop.getGatewayPath() + "/patientBack/updateForm/" + id;

        // Configuration des en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // Effectuer la requête avec les en-têtes
        ResponseEntity<PatientModel> response = restTemplate.exchange(url, HttpMethod.GET, entity, PatientModel.class);
        PatientModel patient = response.getBody();

        model.addAttribute("patient", patient);

        return "patient/update";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Integer id, @Valid PatientModel patient, Model model, @RequestHeader("Authorization") String authHeader) {
        String url = prop.getGatewayPath() + "/patientBack/update/" + id;

        // Configuration des en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<PatientModel> entity = new HttpEntity<>(patient, headers);

        // Effectuer la requête POST avec le corps et les en-têtes
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

        return "redirect:" + prop.getGatewayPath() + "/patientFront/list";
    }

    @GetMapping("/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new PatientModel());
        return "patient/add";
    }

    @PostMapping("/add")
    public String addPatient(@Valid PatientModel patient, Model model, @RequestHeader("Authorization") String authHeader) {
        String url = prop.getGatewayPath() + "/patientBack/add";

        // Configuration des en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<PatientModel> entity = new HttpEntity<>(patient, headers);

        // Effectuer la requête POST avec le corps et les en-têtes
        restTemplate.postForEntity(url, entity, Void.class);

        return "redirect:" + prop.getGatewayPath() + "/patientFront/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id, @RequestHeader("Authorization") String authHeader) {
        String url = prop.getGatewayPath() + "/patientBack/delete/" + id;

        // Configuration des en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // Effectuer la requête DELETE avec les en-têtes, même si c'est dans le contexte d'un @GetMapping
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);

        return "redirect:" + prop.getGatewayPath() + "/patientFront/list";
    }
}
