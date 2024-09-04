package de.hsrm.mi.web.projekt.entities.benutzer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


@Entity
public class Benutzer {
    

    @Id
    @GeneratedValue
    long id;
    
    @Version
    long version;

    @NotEmpty
    String name;

    LocalDate geburtstag;
    
    @NotEmpty
    @Email
    @Column(unique=true)
    String email;

    @NotBlank(message = "{gutespasswort.fehler}")
    String password;

    @ElementCollection(fetch=FetchType.EAGER)
    List<String> magList = new ArrayList<>();
    @ElementCollection(fetch=FetchType.EAGER)
    List<String> magnichtList = new ArrayList<>();

    @OneToMany(mappedBy = "anbieter", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Tour> angeboteneTouren = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Tour> gebuchteTouren = new HashSet<>();

    public Set<Tour> getGebuchteTouren() {
        return gebuchteTouren;
    }

    public void addAngeboteneTour(Tour tour) {
        angeboteneTouren.add(tour);
        tour.setAnbieter(this);
    }

    public void removeAngeboteneTour(Tour tour) {
        angeboteneTouren.remove(tour);
        tour.setAnbieter(null);
    }

    public long getId() {
        return id;
    }
    public long getVersion() {
        return version;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getGeburtstag() {
        return geburtstag;
    }
    public void setGeburtstag(LocalDate geburtstag) {
        this.geburtstag = geburtstag;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        
        if (this.password == null && !password.trim().isEmpty()) {
            this.password = password;
        }else{
            
        }
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

}
