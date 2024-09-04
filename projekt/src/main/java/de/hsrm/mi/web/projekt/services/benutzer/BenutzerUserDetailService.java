package de.hsrm.mi.web.projekt.services.benutzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;
import de.hsrm.mi.web.projekt.entities.benutzer.BenutzerRepository;

@Service
public class BenutzerUserDetailService implements UserDetailsService{
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired BenutzerRepository benutzerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Benutzer user = benutzerRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        String rolle = "USER";
        if(user.getMagList().contains("MACHT")){
            rolle = "CHEF";
        }
        
        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password(user.getPassword())
            .roles(rolle)
            .build();
    }

}
