package de.hsrm.mi.web.projekt.ui.tour;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.entities.tour.Tour;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerService;
import de.hsrm.mi.web.projekt.services.ort.OrtService;
import de.hsrm.mi.web.projekt.services.tour.TourService;
import de.hsrm.mi.web.projekt.ui.benutzer.benutzerController;
import jakarta.validation.Valid;

@Controller
@RequestMapping
@SessionAttributes({"tourformular", "tour"})
public class TourController {

    public final TourService tourService;
    public final BenutzerService benutzerService;
    public final OrtService ortService;
    public static final Logger logger = LoggerFactory.getLogger(TourController.class);

    @Autowired
    public TourController(TourService tourService, BenutzerService benutzerService, OrtService ortService){
        this.tourService = tourService;
        this.benutzerService = benutzerService;
        this.ortService = ortService;
    }


    @ModelAttribute("tourformular")
    public TourFormular getTourFormular(){
        return new TourFormular();
    }

    @ModelAttribute("tour")
    public Tour getTour(){
        return new Tour();
    }

    @GetMapping("/admin/tour")
    public String benutzerliste_get(Locale locale, Model model) {
        
        model.addAttribute("sprache", locale.getDisplayLanguage());

        List<Tour> tourListe = tourService.holeAlleTouren();
    
        model.addAttribute("tourliste", tourListe);

        return  "tourliste";
    }
    
    @GetMapping("/admin/tour/{id}")
    public String benutzer_get(Locale locale, Model model, @PathVariable("id") long id) {
        Tour tour;
        TourFormular tourFormular;
        List<Benutzer> benutzerList = benutzerService.holeAlleBenutzer();
        List<Ort> ortList = ortService.holAlleOrte();
    
        model.addAttribute("sprache", locale.getDisplayLanguage());
        model.addAttribute("info", null);
    
        if (id == 0) {
            tourFormular = new TourFormular();
            tour = new Tour();
            model.addAttribute("ortList", ortList);
            model.addAttribute("benutzerList", benutzerList);
            model.addAttribute("tourformular", tourFormular);
            model.addAttribute("tour", tour);
            
        } else {
            Optional<Tour> optionalTour = tourService.holeTourMitId(id);
            if (optionalTour.isPresent()) {
                tour = optionalTour.get();
                tourFormular = (TourFormular) model.getAttribute("tourformular");
                
                tourFormular.fromTour(tour);
                model.addAttribute("ortList", ortList);
                model.addAttribute("benutzerList", benutzerList);
                model.addAttribute("tourformular", tourFormular);
                model.addAttribute("tour", tour);
            }
        }
        return "tourbearbeiten";
    }
    

    @PostMapping("/admin/tour/{id}")
    public String postMethodName(Model model, @PathVariable("id") long id,
                                 @Valid @ModelAttribute("tourformular") TourFormular tourformular,
                                 BindingResult result) {
                                
        Tour tour = (Tour) model.getAttribute("tour");
        if (result.hasErrors()) {
            return "tourbearbeiten";
        }

        try{
            tourformular.toTour(tour);                       
            tourService.speicherTour(tour,tourformular.getAnbieterId(),tourformular.getStartOrtId(), tourformular.getZielOrtId());

        }catch(Exception x){
            model.addAttribute("info",x.getMessage());
        }
        model.addAttribute("tour", tour);
        model.addAttribute("tourformular", tourformular);

        return "redirect:/admin/tour/" + tour.getId();
    }

    @GetMapping("/admin/tour/{id}/del")
    public String benutzerLoeschenGet(Locale locale, Model model, @PathVariable("id") long id, Principal princ) {
        model.addAttribute("sprache", locale.getDisplayLanguage());
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String loginname = auth.getName();
        Optional<Tour> tourOpt = tourService.holeTourMitId(id);

        if (tourOpt.isPresent()) {
            String anbieter = tourOpt.get().getAnbieter().getEmail();
            logger.info("ANBIETER EMAIL: " + anbieter + " LOGINNAME: " + loginname);
            
            if (loginname.equals(anbieter)) {
                tourService.loescheTourMitId(id);
                model.addAttribute("info", null);
                return "redirect:/admin/tour";
            } else {
                model.addAttribute("info", "Nicht zum Löschen berechtigt!");
                logger.info("Nicht zum Löschen berechtigt!");
                return "redirect:/admin/tour";
            }
        } else {
            model.addAttribute("info", "Tour nicht gefunden!");
            logger.info("Tour nicht gefunden!");
            return "redirect:/admin/tour";
        }
}

    @GetMapping("/admin")
    public String get_admin(Locale locale, Model model) {
        return  "redirect:/login";
    }
}

