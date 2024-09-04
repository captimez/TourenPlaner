package de.hsrm.mi.web.projekt.entities.tour;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.ort.Ort;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
public class Tour {
    
    @GeneratedValue
    @Id
    Long id;

    
    @Version
    Long version;

    LocalDateTime abfahrtsZeit;
    @PositiveOrZero(message ="Darf nicht negativ sein!")
    int preis;
    @Min(value = 1, message = "Es muss min. ein Platz angeboten werden.")
    int plaetze;
    @Size(max = 400, message = "Info darf max. 400 Zeichen habem!")
    String info;

    @ManyToOne(fetch = FetchType.LAZY)
    Benutzer anbieter;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Benutzer> mitfahrgaeste = new HashSet<>();

    

    @NotNull(message = "Darf nicht leer sein!")
    @ManyToOne(fetch = FetchType.LAZY)
    Ort startOrt;
    @NotNull(message = "Darf nicht leer sein!")
    @ManyToOne(fetch = FetchType.LAZY)
    Ort zielOrt;
    
    public Set<Benutzer> getMitfahrgaeste() {
        return mitfahrgaeste;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getAbfahrtsZeit() {
        return abfahrtsZeit;
    }
    public void setAbfahrtsZeit(LocalDateTime abfahrtsZeit) {
        this.abfahrtsZeit = abfahrtsZeit;
    }
    public int getPreis() {
        return preis;
    }
    public void setPreis(int preis) {
        this.preis = preis;
    }
    public int getPlaetze() {
        return plaetze;
    }
    public void setPlaetze(int plaetze) {
        this.plaetze = plaetze;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public Benutzer getAnbieter() {
        return anbieter;
    }
    public void setAnbieter(Benutzer anbieter) {
        this.anbieter = anbieter;
    }
    public Ort getStartOrt() {
        return startOrt;
    }
    public void setStartOrt(Ort startOrt) {
        this.startOrt = startOrt;
    }
    public Ort getZielOrt() {
        return zielOrt;
    }
    public void setZielOrt(Ort zielOrt) {
        this.zielOrt = zielOrt;
    }

    public Long getVersion() {
        return version;
    }
}
