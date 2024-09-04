package de.hsrm.mi.web.projekt.services.benutzer;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;

@Transactional
@Service
public class BenutzerServiceImpl implements BenutzerService {

    private static final Logger logger = LoggerFactory.getLogger(BenutzerServiceImpl.class);

    

    @Autowired  BenutzerRepository benutzerRepo;
    public BenutzerServiceImpl(BenutzerRepository benutzerRepo) {
        
    }

    @Override
    public List<Benutzer> holeAlleBenutzer() {
        logger.info("Fetching all Benutzer, sorted by name.");
        return this.benutzerRepo.findAll(Sort.by("name"));
    }

    @Override
    @Transactional
    public Optional<Benutzer> holeBenutzerMitId(long id) {
        logger.info("Fetching Benutzer with id: {}", id);
        return this.benutzerRepo.findById(id);
    }

    @Override
    public Benutzer speichereBenutzer(Benutzer benutzer) {
        logger.info("Saving Benutzer: {}", benutzer);
        return this.benutzerRepo.save(benutzer);
    }

    @Override
    public void loescheBenutzerMitId(long id) {
        logger.warn("Deleting Benutzer with id: {}", id);
        this.benutzerRepo.deleteById(id);
    }

    @Override
    public Benutzer aktualisiereBenutzerAttribut(long id, String feldname, String wert) {
        Benutzer benutzer = this.benutzerRepo.findById(id).get();
        if(feldname.equals("email")){
            benutzer.setEmail(wert);
        }else if(feldname.equals("name")){
            benutzer.setName(wert);
        }
        logger.info("Feldname {feldname} Wert {wert}",feldname,wert);
        try{
            return this.benutzerRepo.save(benutzer);
        }catch(TransactionSystemException e){
            throw new DataIntegrityViolationException("Nicht akzeptierte Eingabe!");
        }
        
    }
}
