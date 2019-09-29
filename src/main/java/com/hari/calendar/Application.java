package com.hari.calendar;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hari.calendar.frontend.EnglishUI;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        EnglishUI ok = new EnglishUI();
        ok.createAndShowGUI();
        // EnglishToHinduOutput e = new EnglishToHinduOutput("July", 24, 1848);
        // System.err.println(e.getConverted());
        // SpringApplication.run(Application.class, args);
    }

}
