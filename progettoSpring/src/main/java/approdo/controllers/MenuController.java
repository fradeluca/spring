package approdo.controllers;

import approdo.DTO.PizzaDTO;
import approdo.entities.Bibita;
import approdo.entities.Ingrediente;
import approdo.entities.Pizza;
import approdo.exception.*;
import approdo.repositories.PizzaRepository;
import approdo.services.ServizioMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    ServizioMenu servizioMenu;

    @Autowired
    PizzaRepository pr;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add/ingrediente")
    public ResponseEntity aggiungiIngrediente(@Valid @RequestBody Ingrediente ingrediente) {
        try {
            servizioMenu.aggiungiIngrediente(ingrediente);
            return new ResponseEntity("Ingrediente aggiunto", HttpStatus.OK);
        } catch (IngredienteAlreadyExistsException e) {
            return new ResponseEntity("ERROR_INGREDIENT_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/del/ingrediente")
    public ResponseEntity rimuoviIngrediente(@Valid @RequestBody Ingrediente ingrediente) {
        servizioMenu.rimuoviIngrediente(ingrediente);
        return new ResponseEntity("Ingrediente rimosso", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add/pizza")
    public ResponseEntity aggiungiPizza(@Valid @RequestBody PizzaDTO pizza) {
        try {
            Pizza p=new Pizza();
            p.setNome(pizza.getNome());
            p.setPrezzo(pizza.getPrezzo());
            p.setDisponibile(true);
            Set<Ingrediente> ingr=new HashSet<Ingrediente>();
            StringTokenizer st=new StringTokenizer(pizza.getIngredienti(),",",false);
            while(st.hasMoreTokens()) {
                Ingrediente i=new Ingrediente();
                i.setNome(st.nextToken());
                ingr.add(i);
            }
            p.setIngredienti(ingr);
            servizioMenu.aggiungiPizza(p);
            return new ResponseEntity("Pizza aggiunta", HttpStatus.OK);
        } catch (PizzaAlreadyExistsException e) {
            return new ResponseEntity("Pizza già presente", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/apd/pizza")
    public ResponseEntity aggiornaPizza(@Valid @RequestBody PizzaDTO pizza) {
        try {
            Pizza p=new Pizza();
            p.setNome(pizza.getNome());
            p.setPrezzo(pizza.getPrezzo());
            p.setDisponibile(true);
            Set<Ingrediente> ingr=new HashSet<Ingrediente>();
            StringTokenizer st=new StringTokenizer(pizza.getIngredienti(),",",false);
            while(st.hasMoreTokens()) {
                Ingrediente i=new Ingrediente();
                i.setNome(st.nextToken());
                ingr.add(i);
            }
            p.setIngredienti(ingr);
            servizioMenu.aggiornaPizza(p);
            return new ResponseEntity("Pizza Aggiornata", HttpStatus.OK);
        } catch (PizzaNotFoundException e) {
            return new ResponseEntity("Pizza non trovata", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/del/pizza/{nome}")
    public ResponseEntity rimuoviPizza(@PathVariable String nome) {
        if(pr.existsById(nome)) {
            servizioMenu.rimuoviPizza(pr.findById(nome).get());
            return new ResponseEntity("Pizza rimossa", HttpStatus.OK);
        }
        return new ResponseEntity("Pizza non trovata", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/add/bibita")
    public ResponseEntity aggiungiBibita(@Valid @RequestBody Bibita bibita) {
        try {
            servizioMenu.aggiungiBibita(bibita);
            return new ResponseEntity("Bibita aggiunta", HttpStatus.OK);
        } catch (BibitaAlreadyExistsException e) {
            return new ResponseEntity("Bibita già presente", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/apd/bibita")
    public ResponseEntity aggiornaBibita(@Valid @RequestBody Bibita bibita) {
        try {
            servizioMenu.aggiornaBibita(bibita);
            return new ResponseEntity("Bibita aggiornata", HttpStatus.OK);
        } catch (BibitaNotFoundException e) {
            return new ResponseEntity("Bibita non trovata", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/del/bibita")
    public ResponseEntity rimuoviPizza(@Valid @RequestBody Bibita bibita) {
        servizioMenu.rimuoviBibita(bibita);
        return new ResponseEntity("Bibita rimossa", HttpStatus.OK);

    }


    @GetMapping("/pizza")
    public ResponseEntity getAllPizze() {
        List<PizzaDTO> lista=new LinkedList<>();
        for(Pizza p: servizioMenu.getAllPizza()){
            PizzaDTO pdto=new PizzaDTO();
            pdto.setNome(p.getNome());
            pdto.setPrezzo(p.getPrezzo());
            pdto.setIngredienti(p.getIngredienti().toString());
            lista.add(pdto);
        }
        return new ResponseEntity(lista, HttpStatus.OK);

    }
    @GetMapping("/bibita")
    public ResponseEntity getAllBibite() {
        return new ResponseEntity(servizioMenu.getAllBibita(), HttpStatus.OK);
    }
    @GetMapping("/ingrediente")
    public ResponseEntity getAllIngredienti() {
        return new ResponseEntity(servizioMenu.getAllIngrediente(), HttpStatus.OK);
    }

    @GetMapping("/pizza/{nome}")
    public ResponseEntity getAllPizzeByName(@PathVariable String nome) {
        List<PizzaDTO> lista=new LinkedList<>();
        for(Pizza p: servizioMenu.getAllPizzaByName(nome)){
            PizzaDTO pdto=new PizzaDTO();
            pdto.setNome(p.getNome());
            pdto.setPrezzo(p.getPrezzo());
            pdto.setIngredienti(p.getIngredienti().toString());
            lista.add(pdto);
        }
        return new ResponseEntity(lista, HttpStatus.OK);

    }
    @GetMapping("/bibita/{nome}")
    public ResponseEntity getAllBibiteByName(@PathVariable String nome) {
        return new ResponseEntity(servizioMenu.getAllBibitaByName(nome), HttpStatus.OK);
    }
    @GetMapping("/ingrediente/{nome}")
    public ResponseEntity getAllIngredientiByName(@PathVariable String nome) {
        return new ResponseEntity(servizioMenu.getAllIngredienteByName(nome), HttpStatus.OK);
    }
}
