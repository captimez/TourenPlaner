package de.hsrm.mi.web.projekt.ui.ort;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.hsrm.mi.web.projekt.entities.ort.Ort;
import de.hsrm.mi.web.projekt.services.ort.OrtService;
import jakarta.validation.Valid;

@Controller
@RequestMapping
@SessionAttributes({"ortformular", "ort",  "info"})
public class OrtController {

    public final OrtService ortService;

    @Autowired
    public OrtController(OrtService ortService){
        this.ortService = ortService;
    }

    @ModelAttribute("ortformular")
    public OrtFormular getOrtFormular(){
        return new OrtFormular();
    }

    @ModelAttribute("ort")
    public Ort getOrt(){
        return new Ort();
    }

    @GetMapping("/admin/ort")
    public String benutzerliste_get(Locale locale, Model model) {
        
        model.addAttribute("info", null);
        model.addAttribute("sprache", locale.getDisplayLanguage());

        List<Ort> ortListe = ortService.holAlleOrte();
    
        model.addAttribute("ortliste", ortListe);

        return  "ortliste";
    }
    
    @GetMapping("/admin/ort/{id}")
    public String benutzer_get(Locale locale, Model model, @PathVariable("id") long id) {
        Ort ort;
        OrtFormular ortFormular;
    
        model.addAttribute("sprache", locale.getDisplayLanguage());
        model.addAttribute("info","Wenn die Koordinaten leer sind, werden diese Automatisch ergänzt!");
    
        if (id == 0) {
            ortFormular = new OrtFormular();
            ort = new Ort();
            model.addAttribute("ortformular", ortFormular);
            model.addAttribute("ort", ort);
        } else {
            Optional<Ort> optionalOrt = ortService.holeOrtMitId(id);
            if (optionalOrt.isPresent()) {
                ort = optionalOrt.get();
                ortFormular = (OrtFormular) model.getAttribute("ortformular");
                ortFormular.fromOrt(ort);
    
                model.addAttribute("ortformular", ortFormular);
                model.addAttribute("ort", ort);
            }
        }
        return "ortbearbeiten";
    }
    

    @PostMapping("/admin/ort/{id}")
    public String postMethodName(Model model, @PathVariable("id") long id,
                                 @Valid @ModelAttribute("ortformular") OrtFormular ortformular,
                                 BindingResult result) {
                                
        Ort ort = (Ort) model.getAttribute("ort");
        if(ortformular.getGeobreite() == null && ortformular.getGeolaenge() == null){
            List<Ort> vorschlaege = ortService.findeOrtsvorschlaegeFuerAdresse(ortformular.getName());
            if(vorschlaege.isEmpty()){
                model.addAttribute("info", "Kein Ort gefunden!");
                return "ortbearbeiten";
            }else{
                model.addAttribute("info","Nocheinmaml bestätigen damit der Ort gespeichert wird!");
                ortformular.setGeobreite(vorschlaege.getFirst().getGeobreite());
                ortformular.setGeolaenge(vorschlaege.getFirst().getGeolaenge());
                model.addAttribute("ortformular",ortformular);
                return "ortbearbeiten";
            }
        }

        if (result.hasErrors()) {
            return "ortbearbeiten";
        }

        ortformular.toOrt(ort);
        try{
            ort = ortService.speicherOrt(ort);
            
        }catch(Exception x){
            model.addAttribute("info",x.getMessage());
        }
        model.addAttribute("ort", ort);
        model.addAttribute("ortformular", ortformular);

        return "redirect:/admin/ort/" + ort.getId();
    }

    @GetMapping("/admin/ort/{id}/del")
    public String benutzer_loeschen_get(Locale locale, Model model,@PathVariable("id") long id) {
        
        model.addAttribute("sprache", locale.getDisplayLanguage());
        ortService.loescheOrtMitId(id);

        return  "redirect:/admin/ort";
    }
}
