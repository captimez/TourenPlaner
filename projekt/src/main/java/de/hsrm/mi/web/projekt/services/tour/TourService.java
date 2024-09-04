package de.hsrm.mi.web.projekt.services.tour;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.web.projekt.entities.tour.Tour;

public interface TourService{
    List<Tour> holeAlleTouren();
    Optional<Tour> holeTourMitId(long id);
    Tour speicherTour(Tour t,long bId, long sId, long zId);
    void loescheTourMitId(long id);
    Tour generateTour( Tour t,long bId, long sId, long zId);
}