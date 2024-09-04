package de.hsrm.mi.web.projekt.entities.test;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface testRepository extends JpaRepository<test,Long>{
    //ergänzung zu normalen JPA Methoden mit methoden Struktur findByAttribut, implementiert JPA automatisch die SuchMethode
    Optional<test> findfindByLieblingsessen(String lieblingsessen);
}
