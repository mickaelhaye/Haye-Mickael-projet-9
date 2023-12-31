package com.mbackdiabete.microservicebackdiabete.serviceTest;

import com.mbackdiabete.microservicebackdiabete.service.CalculService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test pour CalculService.
 * Effectue des tests unitaires sur les méthodes du service de calcul.
 *
 * @author Mickael Hayé
 * @version 1.0
 */
@SpringBootTest
public class CalculServiceTest {

    @Autowired
    private CalculService calculService;

    /**
     * Teste la méthode getNiveauxRisque pour les patients de plus de 30 ans.
     * Vérifie les différents niveaux de risque basés sur les notes des patients.
     */
    @Test
    void getNiveauxRisquePlus30ansTest() {
        //test avec la date actuelle -31ans
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -31);
        Date dateNaissance = calendar.getTime();
        List<String> notes = new ArrayList<String>();
        notes.add("anormal");
        notes.add("fumeur");
        assertEquals("Borderline", calculService.getNiveauxRisque(dateNaissance, "F", notes));

        notes.clear();
        notes.add("anormal");
        notes.add("fumeur");
        notes.add("Taille");
        notes.add("Poids");
        notes.add("Cholestérol");
        notes.add("Rechute");
        assertEquals("In Danger", calculService.getNiveauxRisque(dateNaissance, "F", notes));

        notes.clear();
        notes.add("Hémoglobine A1C");
        notes.add("Microalbumine");
        notes.add("Taille");
        notes.add("Poids");
        notes.add("Fumeur");
        notes.add("Anormal");
        notes.add("Cholestérol");
        notes.add("Vertiges");
        assertEquals("Early onset", calculService.getNiveauxRisque(dateNaissance, "F", notes));
    }

    /**
     * Teste la méthode getNiveauxRisque pour les patients de moins de 30 ans.
     * Vérifie les différents niveaux de risque basés sur les notes des patients.
     */
    @Test
    void getNiveauxRisqueMoins30ansTest() {
        //test avec la date actuelle -29ans
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, -29);
        Date dateNaissance = calendar.getTime();
        List<String> notes = new ArrayList<String>();
        notes.add("anormal");
        notes.add("fumeur");
        notes.add("Taille");
        assertEquals("In Danger", calculService.getNiveauxRisque(dateNaissance, "H", notes));

        notes.clear();
        notes.add("Hémoglobine A1C");
        notes.add("Microalbumine");
        notes.add("Taille");
        notes.add("Poids");
        notes.add("Fumeur");
        assertEquals("Early onset", calculService.getNiveauxRisque(dateNaissance, "H", notes));

        notes.clear();
        notes.add("Hémoglobine A1C");
        notes.add("Microalbumine");
        notes.add("Taille");
        notes.add("Poids");
        assertEquals("In Danger", calculService.getNiveauxRisque(dateNaissance, "F", notes));

        notes.clear();
        notes.add("Hémoglobine A1C");
        notes.add("Microalbumine");
        notes.add("Taille");
        notes.add("Poids");
        notes.add("Fumeur");
        notes.add("Cholestérol");
        notes.add("Vertiges");
        assertEquals("Early onset", calculService.getNiveauxRisque(dateNaissance, "F", notes));


    }

    /**
     * Teste la méthode calculNbrTermesDeclencheurs.
     * Calcule le nombre de termes déclencheurs dans les notes des patients.
     */
    @Test
    void calculNbrTermesDeclencheursTest() {
        List<String> notes = new ArrayList<String>();
        notes.add("Le patient déclare qu'il fume depuis peu");
        notes.add("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        assertEquals(3, calculService.calculNbrTermesDeclencheurs(notes));

        notes.clear();
        notes.add("LE PATIENT DÉCLARE QU'IL FUME DEPUIS PEU");
        notes.add("LE PATIENT DÉCLARE QU'IL EST FUMEUR ET QU'IL A CESSÉ DE FUMER L'ANNÉE DERNIÈRE. IL SE PLAINT ÉGALEMENT DE CRISES D’APNÉE RESPIRATOIRE ANORMALES. TESTS DE LABORATOIRE INDIQUANT UN TAUX DE CHOLESTÉROL LDL ÉLEVÉ");
        assertEquals(3, calculService.calculNbrTermesDeclencheurs(notes));
    }
}