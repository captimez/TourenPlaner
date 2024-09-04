package de.hsrm.mi.web.projekt.ui.tour;

import java.time.LocalDateTime;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class TourFormular{

    @Positive(message = "{tour.auswahl.fehlt}")
    long anbieterId;
    LocalDateTime abfahrtsZeit;
    @PositiveOrZero
    int preis;
    @Min(value = 1, message ="Es muss min. 1 Platz angeboten werden!")
    int plaetze;
    @Positive(message = "{tour.auswahl.fehlt}")
    long startOrtId;
    @Positive(message = "{tour.auswahl.fehlt}")
    long zielOrtId;
    @Size(max = 400, message ="Darf max. 400 Zeichen haben.")
    String info;

    public void toTour(Tour t){
        t.setAbfahrtsZeit(abfahrtsZeit);
        t.setPreis(preis);
        t.setPlaetze(plaetze);
        t.setInfo(info);
    }

    public void fromTour(Tour t){
        this.setAnbieterId(t.getAnbieter().getId());
        this.setAbfahrtsZeit(t.getAbfahrtsZeit());
        this.setPreis(t.getPreis());
        this.setPlaetze(t.getPlaetze());
        this.setStartOrtId(t.getStartOrt().getId());
        this.setZielOrtId(t.getZielOrt().getId());
        this.setInfo(t.getInfo());
    }


    public long getAnbieterId() {
        return anbieterId;
    }
    public void setAnbieterId(long anbiterId) {
        this.anbieterId = anbiterId;
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
    public long getStartOrtId() {
        return startOrtId;
    }
    public void setStartOrtId(long startOrtId) {
        this.startOrtId = startOrtId;
    }
    public long getZielOrtId() {
        return zielOrtId;
    }
    public void setZielOrtId(long zielOrtId) {
        this.zielOrtId = zielOrtId;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }

}