package com.mbackpatient.microservicebackpatient.controller;

import com.mbackpatient.microservicebackpatient.exceptions.PatientNotFoundException;
import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import com.mbackpatient.microservicebackpatient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("patientBack")

public class PatientController {

    @Autowired
    private PatientService patientService;




    @GetMapping(value = "/list")
    public List<PatientModel> listPatient(@RequestHeader("Authorization") String authHeader){
        decode(authHeader);
        List<PatientModel> patients = (List<PatientModel>) patientService.getPatients();
        return patients;
    }

    public static void decode(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic")) {
            // Retirer le préfixe "Basic" et décoder la chaîne
            String base64Credentials = authHeader.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);

            // Les credentials sont au format "username:password"
            final String[] values = credentials.split(":", 2);

            System.out.println("Username: " + values[0]);
            System.out.println("Password: " + values[1]);
        }
    }

    @GetMapping( value = "/updateForm/{id}")
    public Optional<PatientModel> updatePatientForm(@PathVariable int id,@RequestHeader("Authorization") String authHeader) {
        decode(authHeader);
        Optional<PatientModel> patient = patientService.getPatientById(id);
        if(!patient.isPresent())  throw new PatientNotFoundException("Le patient correspondant à l'id " + id + " n'existe pas");
        return patient;
    }

    @PostMapping(value = "/update/{id}")
    public PatientModel updatePatient(@RequestBody PatientModel updatedPatient, @RequestHeader("Authorization") String authHeader) {
        decode(authHeader);
        return patientService.addPatient(updatedPatient);
    }

    @PostMapping(value = "/add")
    public PatientModel addPatient(@RequestBody PatientModel newPatient, @RequestHeader("Authorization") String authHeader) {
        decode(authHeader);
        return patientService.addPatient(newPatient);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deletePatient(@PathVariable int id, @RequestHeader("Authorization") String authHeader) {
        decode(authHeader);
        patientService.deletePatient(id);
    }

}
