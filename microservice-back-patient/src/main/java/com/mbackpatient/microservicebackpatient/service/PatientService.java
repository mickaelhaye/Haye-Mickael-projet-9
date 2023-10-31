package com.mbackpatient.microservicebackpatient.service;

import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;

import java.util.Optional;

public interface PatientService {
    public Iterable<PatientModel> getPatients();
    public Optional<PatientModel> getPatientById(Integer id);
    public PatientModel addPatient(PatientModel updatedPatient);
    public void deletePatient(int id);
}
