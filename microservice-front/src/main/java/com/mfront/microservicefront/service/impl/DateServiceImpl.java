package com.mfront.microservicefront.service.impl;

import com.mfront.microservicefront.service.DateService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Implémentation du service DateService.
 * Cette classe fournit des fonctionnalités liées à la manipulation et au formatage des dates.
 * Actuellement, elle propose une méthode pour obtenir la date actuelle formatée selon un schéma spécifique.
 *
 * @author mickael hayé
 * @version 1.0
 */
@Service
public class DateServiceImpl implements DateService {
    /**
     * Récupère la date actuelle et la retourne formatée en chaîne de caractères.
     * Le format utilisé est "yyyy-MM-dd".
     *
     * @return String représentant la date actuelle dans le format "yyyy-MM-dd".
     */
    @Override
    public String dateDuJour() {
        // récupération date actuelle
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new java.util.Date());
        String formatDate = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        return sdf.format(calendar.getTime());
    }
}
