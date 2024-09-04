package de.hsrm.mi.web.projekt.ui.test;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/test")
@SessionAttributes(names = {"name"})
public class testController {
    
    @GetMapping("/test/{id}/files")
    public String getMethodName(@PathVariable("id") long id, Model model) {
        List<String> testlist = new ArrayList<>();
        testlist.add(0, "test");
        testlist.add(1, "test");
        testlist.add(2, "test");

        model.addAttribute("testliste", testlist);

        return "testbearbeiten";
    }

    @PutMapping("/test/{id}")
    public String postMethodName(@RequestParam("name") String name, Model model) {
        if(name == "horst"){
            model.addAttribute("name", name);     
        }else{
            String nameZwei = model.getAttribute("name").toString();
        }   
        
        return "testbearbeiten";
    }
    
    

}
