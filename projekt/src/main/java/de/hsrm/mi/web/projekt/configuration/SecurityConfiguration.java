package de.hsrm.mi.web.projekt.configuration;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
public SecurityFilterChain filterChainApp(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(toH2Console()).permitAll()
            .requestMatchers("/admin/ort","/admin/ort/*").hasRole("CHEF")
            .requestMatchers("/admin/**").authenticated()
            .anyRequest().permitAll()
        )
        .formLogin(form -> form
            .defaultSuccessUrl("/admin/benutzer", true) // Zielseite nach erfolgreichem Login
            .permitAll()).csrf(csrf -> csrf
        .ignoringRequestMatchers(toH2Console())
        .ignoringRequestMatchers("/admin/benutzer/*/hx/feld/*")
        .ignoringRequestMatchers("/admin/benutzer/*")
        .ignoringRequestMatchers("/admin/ort/*")
        .ignoringRequestMatchers("/admin/tour/*")
    )
    .headers(hdrs -> hdrs.frameOptions(fo -> fo.sameOrigin()));
        
    return http.build();
}

}
