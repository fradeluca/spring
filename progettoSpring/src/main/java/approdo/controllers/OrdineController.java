package approdo.controllers;


import approdo.DTO.OrdineDTO;
import approdo.entities.*;
import approdo.repositories.UtenteRepository;
import approdo.services.ServizioOrdine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/ordine")
public class OrdineController {

    @Autowired
    ServizioOrdine servizioOrdine;

    @Autowired
    UtenteRepository utrip;

    @PostMapping
    public ResponseEntity addOrder(@Valid @RequestBody OrdineDTO ordine){
        Ordine or=new Ordine();
        or.setUtente(ordine.getUtente());
        or.setPrezzo(ordine.getPrezzo());
        Set<PizzaOrdine> po=new HashSet<>();
        StringTokenizer st=new StringTokenizer(ordine.getPizzaOrdine().replace(", ",",").replace("{","").replace("}",""),",",false);
        while(st.hasMoreTokens()){
            String pda=st.nextToken();
            int index=pda.indexOf(':');
            PizzaOrdine pizzaOrdine=new PizzaOrdine();
            Pizza pizza=new Pizza();
            pizza.setNome(pda.substring(0,index));
            pizzaOrdine.setPizza(pizza);
            pizzaOrdine.setQuantita(Integer.parseInt(pda.substring(index+2)));
            po.add(pizzaOrdine);
        }

        Set<BibitaOrdine> bo=new HashSet<>();
        st=new StringTokenizer(ordine.getBibitaOrdine().replace(", ",",").replace("{","").replace("}",""),",",false);
        while(st.hasMoreElements()){
            String pda=st.nextToken();
            int index=pda.indexOf(':');
            BibitaOrdine bibitaOrdine=new BibitaOrdine();
            Bibita bibita=new Bibita();
            bibita.setNome(pda.substring(0,index));
            bibitaOrdine.setBibita(bibita);
            bibitaOrdine.setQuantita(Integer.parseInt(pda.substring(index+2)));
            bo.add(bibitaOrdine);
        }
        or.setBibitaOrdine(bo);
        or.setPizzaOrdine(po);
        servizioOrdine.addOrdine(or);
        //sendToDipendente
        return new ResponseEntity("ORDINE EFFETTUATO", HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity getOrdiniUtente(@PathVariable int id){
//        List<Ordine> ordini=servizioOrdine.getOrdiniUtente(utrip.findById(id).get());
//        List<OrdineDTO> ordineDTOS=new LinkedList<>();
//        for(Ordine o: ordini){
//            String pos="{";
//            Iterator<PizzaOrdine> it=o.getPizzaOrdine().iterator();
//            while(it.hasNext()){
//                PizzaOrdine po=it.next();
//                pos=pos+po.getPizza().getNome()+":"+ po.getQuantita();
//                if(it.hasNext())
//                    pos=pos+",";
//            }
//            pos=pos+"}";
//            String bos="{";
//            Iterator<BibitaOrdine> itb=o.getBibitaOrdine().iterator();
//            while(itb.hasNext()){
//                BibitaOrdine bo=itb.next();
//                bos=bos+bo.getBibita().getNome()+":"+ bo.getQuantita();
//                if(itb.hasNext())
//                    bos=bos+",";
//            }
//            bos=bos+"}";
//            ordineDTOS.add(new OrdineDTO(o.getUtente(),o.getPrezzo(),pos,bos,o.getData()));
//        }
//        return new ResponseEntity(ordineDTOS, HttpStatus.OK);
//    }

    @GetMapping("/{id}/{page}")
    public ResponseEntity getOrdiniUtente(@PathVariable int id,@PathVariable int page){
        List<Ordine> ordini=servizioOrdine.getOrdiniPaginati(utrip.findById(id).get(),page,5);
        List<OrdineDTO> ordineDTOS=new LinkedList<>();
        for(Ordine o: ordini){
            String pos="{";
            Iterator<PizzaOrdine> it=o.getPizzaOrdine().iterator();
            while(it.hasNext()){
                PizzaOrdine po=it.next();
                pos=pos+po.getPizza().getNome()+":"+ po.getQuantita();
                if(it.hasNext())
                    pos=pos+",";
            }
            pos=pos+"}";
            String bos="{";
            Iterator<BibitaOrdine> itb=o.getBibitaOrdine().iterator();
            while(itb.hasNext()){
                BibitaOrdine bo=itb.next();
                bos=bos+bo.getBibita().getNome()+":"+ bo.getQuantita();
                if(itb.hasNext())
                    bos=bos+",";
            }
            bos=bos+"}";
            ordineDTOS.add(new OrdineDTO(o.getUtente(),o.getPrezzo(),pos,bos,o.getData()));
        }
        return new ResponseEntity(ordineDTOS, HttpStatus.OK);
    }
}
