package de.hsrm.mi.web.projekt.entities.benutzer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    Optional<Benutzer> findByEmail(String email);
}