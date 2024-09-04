package de.hsrm.mi.web.projekt.ui.ort;

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrtFormular {
    
    @NotNull(message = "Darf nicht leer sein!")
    @NotBlank(message ="Darf nicht leer sein!")
    String name;
    Double geobreite;
    Double geolaenge;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getGeobreite() {
        return geobreite;
    }
    public void setGeobreite(Double geobreite) {
        this.geobreite = geobreite;
    }
    public Double getGeolaenge() {
        return geolaenge;
    }
    public void setGeolaenge(Double geolaenge) {
        this.geolaenge = geolaenge;
    }

    public void toOrt(Ort ort){
        ort.setName(this.name);
        ort.setGeobreite(this.geobreite);
        ort.setGeolaenge(this.geolaenge);
    }

    public void fromOrt(Ort ort){
        this.name = ort.getName();
        this.geobreite = ort.getGeobreite();
        this.geolaenge = ort.getGeolaenge();
    }
    

}
