package approdo.repositories;

import approdo.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UtenteRepository extends JpaRepository<Utente,Integer> {


    List<Utente> findByEmail(String email);

    boolean existsByEmail(String email);

}
