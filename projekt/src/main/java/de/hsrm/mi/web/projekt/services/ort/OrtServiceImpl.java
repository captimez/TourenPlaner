package de.hsrm.mi.web.projekt.services.ort;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.entities.ort.OrtRepository;
import de.hsrm.mi.web.projekt.services.geo.GeoAdresse;
import de.hsrm.mi.web.projekt.services.geo.GeoService;

@Service
public class OrtServiceImpl implements OrtService{

    public static final Logger logger = LoggerFactory.getLogger(OrtServiceImpl.class);
    @Autowired OrtRepository ortRepo;
    @Autowired GeoService geoService;
    public OrtServiceImpl(OrtRepository ortRepo, GeoService geoService){

    }

    
    

    @Override
    public List<Ort> holAlleOrte() {
        logger.info("Fetching all Orte");
        return this.ortRepo.findAll();
    }

    @Override
    public Optional<Ort> holeOrtMitId(long id) {
        logger.info("Fetching ort by id" + id);
        return this.ortRepo.findById(id);    
    }

    @Override
    public Ort speicherOrt(Ort o) {
        logger.info("Saving ort: {}", o);
        return this.ortRepo.save(o);
    }

    @Override
    public void loescheOrtMitId(long id) {
        logger.info("Deleting ort by id: " +id );
        this.ortRepo.deleteById(id);    
    }

    @Override
    public List<Ort> findeOrtsvorschlaegeFuerAdresse(String ort) {
        List<Ort> retList = new ArrayList<>();
        Ort temp;
        for(GeoAdresse x: geoService.findeAdressen(ort)){
            temp = new Ort();
            temp.setGeobreite(x.lat());
            temp.setGeolaenge(x.lon());
            temp.setName(x.name());
            retList.add(temp);
        }

        return retList;
    }
}
