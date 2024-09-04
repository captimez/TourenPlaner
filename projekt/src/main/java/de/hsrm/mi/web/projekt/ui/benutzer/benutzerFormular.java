package de.hsrm.mi.web.projekt.ui.benutzer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;



public class benutzerFormular {

    @Size(min = 3, max = 30, message = "Laenge {min} bis {max}")
    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message ="Geburtsdatum darf nicht leer sein!")
    @Past(message = "Geburtsdatum muss in der Vergangenheit liegen!")
    LocalDate geburtstag;
    
    @Email(message = "keine gueltige Email")
    String email;

    String password;
    List<String> magList = new ArrayList<>();
    List<String> magnichtList = new ArrayList<>();
    
    public void toBenutzer(Benutzer b){
        b.setEmail(this.email);
        b.setGeburtstag(this.geburtstag);
        b.setMagList(this.magList);
        b.setMagnichtList(this.magnichtList);
        b.setName(this.name);
        b.setPassword(this.password);
        
    }

    public void fromBenutzer(Benutzer b){
        this.setEmail(b.getEmail());
        this.setGeburtstag(b.getGeburtstag());
        this.setMagList(new ArrayList<>(b.getMagList()));
        this.setMagnichtList(new ArrayList<>(b.getMagnichtList()));
        this.setName(b.getName());
        this.setPassword(b.getPassword());
        
    }


    public List<String> getMagList() {
        return magList;
    }


    public void setMagList(List<String> magList) {
        this.magList = magList;
    }
    
    public List<String> getMagnichtList() {
        return magnichtList;
    }


    public void setMagnichtList(List<String> magnichtList) {
        this.magnichtList = magnichtList;
    }


    public benutzerFormular(){
        
    }


    public String getName() {
        return name;
    }

    public LocalDate getGeburtstag() {
        return geburtstag;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeburtstag(LocalDate geburtstag) {
        this.geburtstag = geburtstag;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
