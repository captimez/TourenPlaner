package de.hsrm.mi.web.projekt.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;

@Configuration
public class DatabaseInitializer {

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired PasswordEncoder passwordEncoder;
    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            if (benutzerRepository.findAll().isEmpty()) {
                Benutzer benutzer1 = new Benutzer();
                benutzer1.setEmail("tim.kl@hotmail.de");
                benutzer1.setName("User One");
                benutzer1.setGeburtstag(LocalDate.of(1999,10,2));
                benutzer1.setPassword(passwordEncoder.encode("1234"));
                benutzer1.getMagList().add("MACHT");
                benutzerRepository.save(benutzer1);

                Benutzer benutzer2 = new Benutzer();
                benutzer2.setEmail("tim@hotmail.de");
                benutzer2.setName("User Two");
                benutzer2.setGeburtstag(LocalDate.of(1999,10,2));
                benutzer2.setPassword(passwordEncoder.encode("1234"));
                benutzerRepository.save(benutzer2);

            }
        };
    }
}

