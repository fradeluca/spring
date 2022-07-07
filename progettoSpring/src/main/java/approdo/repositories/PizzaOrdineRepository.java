package approdo.repositories;

import approdo.entities.PizzaOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaOrdineRepository extends JpaRepository<PizzaOrdine,Integer> {
}
