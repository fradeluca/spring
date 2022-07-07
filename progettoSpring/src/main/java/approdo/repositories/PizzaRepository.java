package approdo.repositories;

import approdo.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PizzaRepository extends JpaRepository<Pizza,String> {

    List<Pizza> findByOrderByPrezzoAsc();
    List<Pizza> findByOrderByPrezzoDesc();

    List<Pizza> findAllByDisponibile(boolean disp);

    @Query("select p from Pizza p where p.nome like :nome% and p.disponibile=true")
    List<Pizza> findByNome(@Param("nome") String nome);
}
