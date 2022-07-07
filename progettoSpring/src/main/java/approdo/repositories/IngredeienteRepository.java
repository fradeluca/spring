package approdo.repositories;

import approdo.entities.Ingrediente;
import approdo.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredeienteRepository extends JpaRepository<Ingrediente,String> {

    @Query("select p from Ingrediente p where p.nome like :nome%")
    List<Ingrediente> findByNome(@Param("nome") String nome);

}
