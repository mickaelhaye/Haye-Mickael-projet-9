package com.mbackdiabete.microservicebackdiabete.service.impl;

import com.mbackdiabete.microservicebackdiabete.configuration.CustomProperties;
import com.mbackdiabete.microservicebackdiabete.controller.DiabeteController;
import com.mbackdiabete.microservicebackdiabete.service.CalculService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implémentation du service de calcul pour le microservice back-diabete.
 * Cette classe fournit des méthodes pour calculer le niveau de risque de diabète en fonction de différents paramètres.
 *
 * @author Mickael Hayé
 * @version 1.0
 */
@Service
public class CalculServiceImpl implements CalculService {
    private final CustomProperties prop;
    private static final Logger logger = LoggerFactory.getLogger(DiabeteController.class);

    /**
     * Constructeur pour initialiser les propriétés du service.
     *
     * @param prop Les propriétés personnalisées utilisées par le service.
     */
    public CalculServiceImpl(CustomProperties prop) {
        this.prop = prop;
    }

    /**
     * Évalue le niveau de risque de diabète d'un patient en fonction de son âge, son genre, et ses notes médicales.
     *
     * @param dateNaissance La date de naissance du patient.
     * @param genre         Le genre du patient.
     * @param notes         Liste des notes médicales du patient.
     * @return String représentant le niveau de risque de diabète.
     */
    @Override
    public String getNiveauxRisque(Date dateNaissance, String genre, List<String> notes) {
        logger.info("Calcul du niveau de risque de diabète pour la date de naissance: {} et le genre: {}", dateNaissance, genre);
        //Par défaut aucun risque
        String niveauxRisque = "None";
        int age = calculAge(dateNaissance);
        int nbrTermesDeclencheurs = calculNbrTermesDeclencheurs(notes);

        if (age >= 30) {
            //si supérieur à 30 ans et Entre 2 et 5 notes = Risque Limité
            if (nbrTermesDeclencheurs >= 2 && nbrTermesDeclencheurs <= 5) {
                niveauxRisque = "Borderline";
            }
            //si supérieur à 30 ans et Entre 6 et 7 notes = Danger
            if (nbrTermesDeclencheurs >= 6 && nbrTermesDeclencheurs <= 7) {
                niveauxRisque = "In Danger";
            }
            //si supérieur à 30 ans et Entre 8 et + notes = Apparition Précoce
            if (nbrTermesDeclencheurs >= 8) {
                niveauxRisque = "Early onset";
            }
        }
        if (age < 30) {
            if (genre.equals("H")) {
                //si inférieur à 30 ans et Homme et Entre 3 et 4 notes = Danger
                if (nbrTermesDeclencheurs >= 3 && nbrTermesDeclencheurs <= 4) {
                    niveauxRisque = "In Danger";
                }
                //si inférieur à 30 ans et Homme et Entre 5 et + notes = Apparition Précoce
                if (nbrTermesDeclencheurs >= 5) {
                    niveauxRisque = "Early onset";
                }
            }
            if (genre.equals("F")) {
                //si inférieur à 30 ans et Femme et Entre 4 et 7 notes = Danger
                if (nbrTermesDeclencheurs >= 4 && nbrTermesDeclencheurs <= 7) {
                    niveauxRisque = "In Danger";
                }
                //si inférieur à 30 ans et Femme et Entre 7 et + notes = Apparition Précoce
                if (nbrTermesDeclencheurs >= 7) {
                    niveauxRisque = "Early onset";
                }
            }
        }
        logger.info("Niveau de risque calculé: {}", niveauxRisque);
        return niveauxRisque;
    }

    /**
     * Calcule le nombre de termes déclencheurs trouvés dans les notes médicales du patient.
     *
     * @param notes Liste des notes médicales du patient.
     * @return int Le nombre de termes déclencheurs trouvés.
     */
    @Override
    public int calculNbrTermesDeclencheurs(List<String> notes) {
        logger.info("Calcul du nombre de termes déclencheurs dans les notes médicales.");
        //Mise en minuscule de la liste des notes
        List<String> notesInLowerCase = notes.stream().map(String::toLowerCase).collect(Collectors.toList());
        //Chargement Liste des expressions à rechercher
        String filePath = prop.getTermesDiabeteFilePath();
        List<String> ExpressionsInLowerCase = new ArrayList<>();
        try {
            // Création du BufferedReader pour lire le fichier
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            // Lecture du fichier ligne par ligne
            while ((line = reader.readLine()) != null) {
                // Ajout en minuscule des termes à la liste, en les séparant par le point-virgule
                ExpressionsInLowerCase.addAll(Arrays.asList(line.toLowerCase().split(";")));
            }

            // Fermeture du BufferedReader
            reader.close();
        } catch (IOException e) {
            logger.info("Problème avec l'accès au fichier contenant la liste des expressions de recherche diabète");
        }

        //Les familles d'expression contenues dans la liste de notes sont chargées dans la liste ExpressionsContenus
        Set<String> ExpressionsContenus = new HashSet<>();
        for (String note : notesInLowerCase) {
            for (String expressionFamille : ExpressionsInLowerCase) {
                List<String> expressionList = Arrays.stream(expressionFamille.split(",")).collect(Collectors.toList());
                for (String expression2 : expressionList) {
                    if (note.contains(expression2)) {
                        ExpressionsContenus.add(expressionFamille);
                        break;
                    }
                }
            }
        }
        logger.info("Nombre de termes déclencheurs trouvés: {}", ExpressionsContenus.size());
        return ExpressionsContenus.size();
    }

    /**
     * Calcule l'âge du patient à partir de sa date de naissance.
     *
     * @param dateNaissance La date de naissance du patient.
     * @return int L'âge du patient en années.
     */
    @Override
    public int calculAge(Date dateNaissance) {
        logger.info("Calcul de l'âge pour la date de naissance: {}", dateNaissance);
        // calcul de l'age
        // récupération date actuelle
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String formatDate = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        String dateActuelle = sdf.format(calendar.getTime());

        Calendar calStr1 = Calendar.getInstance();
        Calendar calStr2 = Calendar.getInstance();

        Date date2;
        int nbMois=0;
        int nbAnnees=0;

        try {
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(dateActuelle);
        } catch (ParseException e) {
            return -999;
        }
        calStr1.setTime(dateNaissance);
        calStr2.setTime(date2);

        while (calStr1.before(calStr2)) {
            calStr1.add(GregorianCalendar.MONTH, 1);
            if (calStr1.before(calStr2) || calStr1.equals(calStr2)) {
                nbMois++;
            }
        }
        nbAnnees = (nbMois / 12);
        logger.info("Âge calculé: {}", nbAnnees);
        return nbAnnees;
    }
}
