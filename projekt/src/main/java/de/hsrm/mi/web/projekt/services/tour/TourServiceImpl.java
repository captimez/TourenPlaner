package de.hsrm.mi.web.projekt.services.tour;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.entities.tour.TourRepository;
import de.hsrm.mi.web.projekt.messaging.FrontendNachrichtEvent;
import de.hsrm.mi.web.projekt.messaging.FrontendNachrichtService;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerService;
import de.hsrm.mi.web.projekt.services.ort.OrtService;
import de.hsrm.mi.web.projekt.services.ort.OrtServiceImpl;



@Service
class TourServiceImpl implements TourService{

    public static final Logger logger = LoggerFactory.getLogger(TourServiceImpl.class);
    
    @Autowired TourRepository tourRepo;
    @Autowired BenutzerService benutzerService;
    @Autowired OrtService ortService;
    @Autowired FrontendNachrichtService nachrichtenService;
    
    TourServiceImpl(BenutzerService benutzerService, OrtService ortService){
        this.benutzerService = benutzerService;
        this.ortService = ortService;
    }
    @Override
    public List<Tour> holeAlleTouren() {
        return tourRepo.findAll();
    }

    @Override
    public Optional<Tour> holeTourMitId(long id) {
        return tourRepo.findById(id);
    }

    @Transactional
    @Override
    public Tour speicherTour(Tour t, long bId, long sId, long zId) {
         t = generateTour(t, bId, sId, zId);
         nachrichtenService.sendEvent(new FrontendNachrichtEvent(FrontendNachrichtEvent.Nachrichtentyp.TOUR, zId, FrontendNachrichtEvent.Operation.CREATE));
         logger.info("IMPORTANT[]" + new FrontendNachrichtEvent(FrontendNachrichtEvent.Nachrichtentyp.TOUR, zId, FrontendNachrichtEvent.Operation.CREATE) );
        return tourRepo.save(t);
    }

    @Override
    public void loescheTourMitId(long id) {
        nachrichtenService.sendEvent(new FrontendNachrichtEvent(FrontendNachrichtEvent.Nachrichtentyp.TOUR, id, FrontendNachrichtEvent.Operation.DELETE));
        tourRepo.deleteById(id);
        
    }

    @Transactional
    @Override
    public Tour generateTour( Tour t,long bId, long sId, long zId){
        Optional<Benutzer> benutzerOpt = benutzerService.holeBenutzerMitId(bId);
        Optional<Ort> startOrtOpt = ortService.holeOrtMitId(sId);
        Optional<Ort> zielOrtOpt = ortService.holeOrtMitId(zId);

        if (!benutzerOpt.isPresent() || !startOrtOpt.isPresent() || !zielOrtOpt.isPresent()) {
            throw new IllegalArgumentException("Benutzer, StartOrt, or ZielOrt not found");
        }

        Benutzer benutzer = benutzerOpt.get();
        Ort startOrt = startOrtOpt.get();
        Ort zielOrt = zielOrtOpt.get();

        t.setAnbieter(benutzer);
        t.setStartOrt(startOrt);
        t.setZielOrt(zielOrt);

        benutzer.addAngeboteneTour(t);

        return t;
    }

}