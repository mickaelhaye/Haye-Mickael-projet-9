package com.mbackdiabete.microservicebackdiabete.service;

import java.util.Date;
import java.util.List;

/**
 * Interface définissant les services de calcul pour le microservice back-diabete.
 * Fournit des méthodes pour évaluer le risque de diabète et effectuer des calculs liés à la santé du patient.
 *
 * @author Mickael Hayé
 * @version 1.0
 */
public interface CalculService {
    /**
     * Calcule le niveau de risque de diabète pour un patient donné.
     * Le calcul est basé sur la date de naissance, le genre du patient, et ses notes médicales.
     *
     * @param dateNaissance La date de naissance du patient.
     * @param genre         Le genre du patient (ex. "H" pour homme, "F" pour femme).
     * @param notes         Une liste de notes médicales concernant le patient.
     * @return String représentant le niveau de risque de diabète du patient.
     */
    String getNiveauxRisque(Date dateNaissance, String genre, List<String> notes);

    /**
     * Calcule le nombre de termes déclencheurs de diabète présents dans les notes médicales du patient.
     * Cette méthode est utile pour déterminer le niveau de risque basé sur la fréquence de certains termes.
     *
     * @param notes Liste des notes médicales du patient.
     * @return int Le nombre de termes déclencheurs trouvés dans les notes.
     */
    int calculNbrTermesDeclencheurs(List<String> notes);

    /**
     * Calcule l'âge du patient à partir de sa date de naissance.
     * Cette méthode est essentielle pour évaluer les risques de santé liés à l'âge.
     *
     * @param dateNaissance La date de naissance du patient.
     * @return int L'âge du patient en années.
     */
    int calculAge(Date dateNaissance);
}
