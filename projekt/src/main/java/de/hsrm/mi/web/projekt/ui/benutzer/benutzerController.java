package de.hsrm.mi.web.projekt.ui.benutzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.services.benutzer.BenutzerService;
import jakarta.validation.Valid;


@Controller
@RequestMapping
@SessionAttributes({"formular", "benutzer"})
public class benutzerController {

    public int maxWunsch = 5;
    public final BenutzerService benutzerService;
    public static final Logger logger = LoggerFactory.getLogger(benutzerController.class);
    
    @Autowired PasswordEncoder passwordEncoder;

    @Autowired
    public benutzerController(BenutzerService benutzerService){
        
        this.benutzerService = benutzerService;
    }

    @ModelAttribute("formular")
    public benutzerFormular getBenutzerFormular() {
        return new benutzerFormular();
    }

    @ModelAttribute("benutzer")
    public Benutzer getBenutzer(){
        return new Benutzer();
    }

    @ModelAttribute("magListe")
    public List<String> getMagListe(){
        return new ArrayList<>();
    }

    @ModelAttribute("magnichtListe")
    public List<String> getMagNichtListe(){
        return new ArrayList<>();
    }

    @GetMapping("/admin/benutzer")
    public String benutzerliste_get(Locale locale, Model model) {
        model.addAttribute("sprache", locale.getDisplayLanguage());
        model.addAttribute("info", null);
        
        List<Benutzer> benutzerListe = benutzerService.holeAlleBenutzer();
        Collections.sort(benutzerListe, Comparator
            .comparing(Benutzer::getName)
            .thenComparing(Benutzer::getEmail));

        model.addAttribute("benutzerliste", benutzerListe);

        return  "benutzerliste";
    }

    @GetMapping("/admin/benutzer/{id}/del")
    public String benutzer_loeschen_get(Locale locale, Model model,@PathVariable("id") long id) {
        
        model.addAttribute("sprache", locale.getDisplayLanguage());
        benutzerService.loescheBenutzerMitId(id);

        return  "redirect:/admin/benutzer";
    }

    @GetMapping("/admin/benutzer/{id}/hx/feld/{feldname}")
    public String getMethodName(Locale locale, Model model, @PathVariable("id") long id, @PathVariable("feldname") String feldname) {
        Benutzer benutzer = this.benutzerService.holeBenutzerMitId(id).get();
        model.addAttribute("benutzerid", id);
        model.addAttribute("feldname", feldname);
        switch(feldname){
            case "email":
                model.addAttribute("wert", benutzer.getEmail());
                break;
            case "name":
                model.addAttribute("wert", benutzer.getName());
                break;
        }

        return "fragments/benutzerliste-zeile :: feldbearbeiten";
    }

    @PutMapping("/admin/benutzer/{id}/hx/feld/{feldname}")
    public String putMethodName(Model model, @PathVariable("id") long id, @PathVariable("feldname") String feldname, @RequestParam("wert") String wert) {
        Benutzer benutzer;
        try{

            benutzer = benutzerService.aktualisiereBenutzerAttribut(id,feldname,wert);
            model.addAttribute("hasError", false);

        }catch(Exception e){

            model.addAttribute("hasError", true);

            benutzer = benutzerService.holeBenutzerMitId(id).get();
            logger.info("feldname: "+ feldname);
            model.addAttribute("benutzerid", id);
            model.addAttribute("feldname", feldname);

            switch(feldname){
                case "email":
                    model.addAttribute("wert", benutzer.getEmail());
                    break;
                case "name":
                    model.addAttribute("wert", benutzer.getName());
                    break;
            }
            
            return "fragments/benutzerliste-zeile :: feldbearbeiten";
        }
        
            model.addAttribute("benutzerid", id);
            model.addAttribute("feldname", feldname);
            model.addAttribute("wert", wert);

            return "fragments/benutzerliste-zeile :: feldausgeben";
        
    }
    
    

    @GetMapping("/admin/benutzer/{id}")
    public String benutzer_get(Locale locale, Model model, @PathVariable("id") long id) {
        Benutzer benutzer;
        benutzerFormular formular;
        
        model.addAttribute("info", null);
        model.addAttribute("maxWunsch", this.maxWunsch);
        model.addAttribute("sprache", locale.getDisplayLanguage());
    
        if (id == 0) {
            formular = new benutzerFormular();
            benutzer = new Benutzer();
            model.addAttribute("formular", formular);
            model.addAttribute("benutzer", benutzer);
        } else {
            Optional<Benutzer> optionalBenutzer = benutzerService.holeBenutzerMitId(id);
            if (optionalBenutzer.isPresent()) {
                benutzer = optionalBenutzer.get();
                formular = (benutzerFormular) model.getAttribute("formular");
                formular.fromBenutzer(benutzer);
    
                model.addAttribute("formular", formular);
                model.addAttribute("benutzer", benutzer);
            }
        }
        return "benutzerbearbeiten";
    }
    

    @PostMapping("/admin/benutzer/{id}")
    public String postMethodName(Model model, @PathVariable("id") long id,
                                 @RequestParam(name = "magich", required = false) String magich,
                                 @RequestParam(name = "magichnicht", required = false) String magichnicht,
                                 @Valid @ModelAttribute("formular") benutzerFormular formular,
                                 BindingResult result) {
                                
        List<String> magList = formular.getMagList();
        List<String> magnichtList = formular.getMagnichtList();
        Benutzer benutzer = (Benutzer) model.getAttribute("benutzer");
        

        if (magich != null && !magich.isEmpty() && magList.size() < this.maxWunsch) {
            magList.add(magich);
        }

        if (magichnicht != null && !magichnicht.isEmpty() && magnichtList.size() < this.maxWunsch) {
            magnichtList.add(magichnicht);
        }

        if (formular.getPassword().isEmpty() && benutzer.getPassword() == null) {
            result.rejectValue("password", "benutzer.password.ungesetzt", "Passwort wurde noch nicht gesetzt");
        }

        if (result.hasErrors()) {
            return "benutzerbearbeiten";
        }

        formular.setPassword(passwordEncoder.encode(formular.getPassword()));
        formular.toBenutzer(benutzer);
        try{
            benutzer = benutzerService.speichereBenutzer(benutzer);
            
        }catch(Exception x){
            model.addAttribute("info",x.getMessage());
        }
        model.addAttribute("benutzer", benutzer);
        model.addAttribute("formular", formular);

        return "redirect:/admin/benutzer/" + benutzer.getId();
    }
}
