package approdo.repositories;

import approdo.entities.BibitaOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibitaOrdineRepository extends JpaRepository<BibitaOrdine,Integer> {
}
