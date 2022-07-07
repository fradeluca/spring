package approdo.services;

import approdo.entities.Bibita;
import approdo.entities.Ingrediente;
import approdo.exception.*;
import approdo.entities.Pizza;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import approdo.repositories.BibitaRepository;
import approdo.repositories.IngredeienteRepository;
import approdo.repositories.PizzaRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class ServizioMenu {

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    IngredeienteRepository ingredienteRepository;

    @Autowired
    BibitaRepository bibitaRepository;

    @Autowired
    EntityManager entityManager;


    @Transactional(readOnly = false)
    public Pizza aggiungiPizza(Pizza p) throws PizzaAlreadyExistsException {
        if(pizzaRepository.existsById(p.getNome()))
            throw new PizzaAlreadyExistsException();
        for(Ingrediente in: p.getIngredienti())
            if(!ingredienteRepository.existsById(in.getNome()))
                ingredienteRepository.save(in);
        return pizzaRepository.save(p);

    }

    @Transactional(readOnly = false)
    public Bibita aggiungiBibita(Bibita b) throws BibitaAlreadyExistsException {
        if(b.getNome()!=null&&bibitaRepository.existsByNome(b.getNome()))
            throw new BibitaAlreadyExistsException();
        return bibitaRepository.save(b);

    }

    @Transactional(readOnly = false)
    public Ingrediente aggiungiIngrediente(Ingrediente ingr) throws IngredienteAlreadyExistsException {
        if(ingr.getNome()!=null&&ingredienteRepository.existsById(ingr.getNome()))
            throw new IngredienteAlreadyExistsException();
        return ingredienteRepository.save(ingr);

    }

    @Transactional(readOnly = false)
    public void rimuoviPizza(Pizza p) {
        if(pizzaRepository.existsById(p.getNome())){
            Pizza pizza= pizzaRepository.getOne(p.getNome());
            pizza.setDisponibile(false);
            pizzaRepository.save(pizza);
            //pizzaRepository.delete(p);
        }

    }

    @Transactional(readOnly = false)
    public void rimuoviBibita(Bibita b)  {
        if(b.getNome()!=null&&bibitaRepository.existsByNome(b.getNome()))
            bibitaRepository.delete(b);
    }

    @Transactional(readOnly = false)
    public void rimuoviIngrediente(Ingrediente i)  {
        if(i.getNome()!=null&&ingredienteRepository.existsById(i.getNome()))
            ingredienteRepository.delete(i);
    }


    @Transactional(readOnly = false)
    public Pizza aggiornaPizza(Pizza p) throws PizzaNotFoundException {
        if(!pizzaRepository.existsById(p.getNome()))
            throw new PizzaNotFoundException();
        for(Ingrediente i: p.getIngredienti()) {
            if (!ingredienteRepository.existsById(i.getNome()))
                ingredienteRepository.save(i);
        }
        return pizzaRepository.save(p);

    }

    @Transactional(readOnly = false)
    public Bibita aggiornaBibita(Bibita b) throws BibitaNotFoundException {
        if(b.getNome()!=null&&!bibitaRepository.existsByNome(b.getNome()))
            throw new BibitaNotFoundException();
        return bibitaRepository.save(b);

    }
    @Transactional(readOnly = true)
    public List<Pizza> getAllPizza(){
        return pizzaRepository.findAllByDisponibile(true);
    }
    @Transactional(readOnly = true)
    public List<Bibita> getAllBibita(){
        return bibitaRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Ingrediente> getAllIngrediente(){
        return ingredienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Pizza> getAllPizzaByName(String nome){
        return pizzaRepository.findByNome(nome);
    }
    @Transactional(readOnly = true)
    public List<Bibita> getAllBibitaByName(String nome){
        return bibitaRepository.findByNome(nome);
    }
    @Transactional(readOnly = true)
    public List<Ingrediente> getAllIngredienteByName(String nome){ return ingredienteRepository.findByNome(nome);
    }

}
