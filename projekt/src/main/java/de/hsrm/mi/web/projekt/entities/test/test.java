package de.hsrm.mi.web.projekt.entities.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class test {
    @Id
    @GeneratedValue
    long id;

    @NotBlank(message ="name darf nicht leer sein!")
    String name;

    @NotNull
    String liebslingsessen;
    @Email
    String email;
}
