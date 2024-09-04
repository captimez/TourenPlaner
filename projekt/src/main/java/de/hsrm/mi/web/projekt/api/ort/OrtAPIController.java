package de.hsrm.mi.web.projekt.api.ort;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;



import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.services.ort.OrtService;


@Controller
@RequestMapping
public class OrtAPIController {
    @Autowired
    OrtService ortService;

    @GetMapping("/api/ort")
    @ResponseBody
     public List<OrtDTO> getAlleOrte() {
        List<Ort> alleOrte = ortService.holAlleOrte();
        return alleOrte.stream()
                .map(OrtDTO::fromOrt)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/api/ort/{id}")
    @ResponseBody
    public OrtDTO getOrtById(@PathVariable("id") long id) {
        Ort ort = ortService.holeOrtMitId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ort not found"));
        return OrtDTO.fromOrt(ort);
    }
    

}
