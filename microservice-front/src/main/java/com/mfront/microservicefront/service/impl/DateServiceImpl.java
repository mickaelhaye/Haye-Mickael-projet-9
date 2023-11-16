package com.mfront.microservicefront.service.impl;

import com.mfront.microservicefront.service.DateService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Service
public class DateServiceImpl implements DateService {
    @Override
    public String dateDuJour() {
        // récupération date actuelle
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new java.util.Date());
        String formatDate = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        String dateActuelle = sdf.format(calendar.getTime());
        return dateActuelle;
    }
}
