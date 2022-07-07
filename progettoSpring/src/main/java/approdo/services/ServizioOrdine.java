package approdo.services;

import approdo.entities.BibitaOrdine;
import approdo.entities.Ordine;
import approdo.entities.PizzaOrdine;
import approdo.entities.Utente;

import approdo.repositories.BibitaOrdineRepository;
import approdo.repositories.PizzaOrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import approdo.repositories.OrdineRepository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServizioOrdine {

    @Autowired
    EntityManager em;

    @Autowired
    OrdineRepository ordineRepository;

    @Autowired
    PizzaOrdineRepository pizzaOrdineRepository;

    @Autowired
    BibitaOrdineRepository bibitaOrdineRepository;

    @Transactional(readOnly = false,rollbackFor = {Exception.class})
    public Ordine addOrdine(Ordine ordine)  {
        ordine.setData(LocalDateTime.now());
        Ordine result = ordineRepository.save(ordine);
        for ( PizzaOrdine po : result.getPizzaOrdine() ) {
            po.setOrdine(result);
            PizzaOrdine pizza = pizzaOrdineRepository.save(po);
            //em.lock(po.getPizza(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        }
        for ( BibitaOrdine bo : result.getBibitaOrdine()) {
            bo.setOrdine(result);
            BibitaOrdine bibita = bibitaOrdineRepository.save(bo);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniUtente(Utente user){
        return ordineRepository.findByUtenteOrderByDataDesc(user);
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniUtenteAndData(Utente user, LocalDateTime inizio, LocalDateTime fine){
        return ordineRepository.findByUtenterInPeriod(inizio,fine,user);
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniPaginati(Utente user,int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by("Data"));
        List<Ordine> pagedResult = ordineRepository.findAllByUtenteOrderByDataDesc(user,paging);
        if ( !pagedResult.isEmpty() ) {
            return pagedResult;
        }
        else {
            return new ArrayList<>();
        }
    }


}
