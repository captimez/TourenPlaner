package de.hsrm.mi.web.projekt.services.tour;

import java.time.LocalDateTime;
import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.services.geo.GeoDistanz;

public record TourDTD(
    long id, 
    LocalDateTime abfahrDateTime,
    int preis,
    int plaetze,
    int buchungen,
    String startOrtName,
    long startOrtId,
    String zielOrtName,
    long zielOrtId,
    String anbieterName,
    long anbieterId,
    double distanz,
    String info
){
    public static TourDTD fromTour(Tour t){
        GeoDistanz geo = new GeoDistanz();
        double distance = geo.calculateDistance(t.getStartOrt().getGeobreite(),t.getStartOrt().getGeolaenge(), t.getZielOrt().getGeobreite(), t.getZielOrt().getGeolaenge());
        return new TourDTD(t.getId(), t.getAbfahrtsZeit(), t.getPreis(), t.getPlaetze(),t.getMitfahrgaeste().size(), t.getStartOrt().getName(), t.getStartOrt().getId(), t.getZielOrt().getName(),  t.getZielOrt().getId(), t.getAnbieter().getName(), t.getAnbieter().getId(), distance,t.getInfo());
    }

}


