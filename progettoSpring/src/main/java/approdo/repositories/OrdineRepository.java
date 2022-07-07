package approdo.repositories;

import approdo.entities.Utente;
import approdo.entities.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface OrdineRepository extends JpaRepository<Ordine,Integer> {

    List<Ordine> findByUtenteOrderByDataDesc(Utente user);

    List<Ordine> findAllByUtenteOrderByDataDesc(Utente u, Pageable pagina);

    @Query("select o from Ordine o where o.data > ?1 and o.data < ?2 and o.utente = ?3")
    List<Ordine> findByUtenterInPeriod(LocalDateTime startDate, LocalDateTime endDate, Utente user);


}
