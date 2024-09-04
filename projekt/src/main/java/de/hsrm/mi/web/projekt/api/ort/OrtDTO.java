package de.hsrm.mi.web.projekt.api.ort;

import de.hsrm.mi.web.projekt.entities.ort.Ort;

public record OrtDTO( long id, String name, double geoBreite, double geoLaenge) {
    public static OrtDTO fromOrt(Ort o){
        return new OrtDTO(o.getId(),o.getName(),o.getGeobreite(),o.getGeolaenge());
    }

    public static Ort fromOrtDTO(OrtDTO dto){
        Ort ort = new Ort();
        ort.setGeobreite(dto.geoBreite());
        ort.setGeolaenge(dto.geoLaenge());
        ort.setName(dto.name());
        
        return ort;
    }

    @Override
    public String toString(){
        return "{ 'id': {id}, 'name': '{name}', 'geoBreite': {geoBreite}, 'geoLaenge': {geoLaenge} }".formatted(id,name,geoBreite,geoLaenge);
    }
}
