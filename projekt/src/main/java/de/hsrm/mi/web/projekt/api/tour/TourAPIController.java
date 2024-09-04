package de.hsrm.mi.web.projekt.api.tour;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.services.tour.TourDTD;
import de.hsrm.mi.web.projekt.services.tour.TourService;


@Controller
@RequestMapping
public class TourAPIController {

    @Autowired
    TourService tourService;

    @GetMapping("/api/tour")
    @ResponseBody
    public List<TourDTD> getAlleTouren() {
        List<Tour> alleTouren = tourService.holeAlleTouren();
        return alleTouren.stream()
            .map(TourDTD::fromTour)
            .collect(Collectors.toList());
    }
    
}
