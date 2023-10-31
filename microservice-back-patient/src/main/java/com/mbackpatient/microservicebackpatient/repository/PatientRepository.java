package com.mbackpatient.microservicebackpatient.repository;

import com.mbackpatient.microservicebackpatient.model.entity.PatientModel;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<PatientModel, Integer> {
}
