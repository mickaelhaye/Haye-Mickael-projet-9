package com.mbackpatient.microservicebackpatient.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientInfoDTO {

    /**
     * Date de naissance du patient, stockée sous forme de chaîne.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;

    /**
     * Genre du patient.
     */
    private String genre;
}
