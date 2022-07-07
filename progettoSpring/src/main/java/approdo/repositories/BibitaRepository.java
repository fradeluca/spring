package approdo.repositories;

import approdo.entities.Bibita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BibitaRepository extends JpaRepository<Bibita,String> {

    List<Bibita> findByOrderByPrezzoAsc();
    List<Bibita> findByOrderByPrezzoDesc();
    @Query("select p from Bibita p where p.nome like :nome%")
    List<Bibita> findByNome(@Param("nome") String nome);

    boolean existsByNome(String nome);
}
