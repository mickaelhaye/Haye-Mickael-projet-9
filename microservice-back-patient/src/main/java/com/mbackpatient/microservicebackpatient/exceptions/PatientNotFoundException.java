package com.mbackpatient.microservicebackpatient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lancée lorsque le patient n'est pas trouvé.
 * Elle retourne un code HTTP "NOT_FOUND" lorsque cette exception est lancée.
 *
 * @author mickael hayé
 * @version 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {

    /**
     * Construit une nouvelle instance de {@link PatientNotFoundException}.
     *
     * @param message Le message d'erreur à associer à l'exception.
     */
    public PatientNotFoundException(String message) {
        super(message);
    }
}
