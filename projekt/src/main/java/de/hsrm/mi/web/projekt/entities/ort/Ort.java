package de.hsrm.mi.web.projekt.entities.ort;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ort {

    @GeneratedValue
    @Id
    Long id;

    @Version
    Long version;
    @NotNull
    @NotBlank
    String name;
    Double geobreite;
    Double geolaenge;
    
    public Long getId() {
        return id;
    }
    
    public Long getVersion() {
        return version;
    }
    
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
}
