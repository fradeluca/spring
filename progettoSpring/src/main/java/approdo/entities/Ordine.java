package approdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "ordine")
public class Ordine {

    @Id
    @JsonIgnore
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int Id;

    @Column(name = "prezzo",nullable = false)
    private double prezzo;

    @OneToMany(mappedBy = "ordine",fetch = FetchType.EAGER)
    private Set<PizzaOrdine> pizzaOrdine;

    @OneToMany(mappedBy = "ordine",fetch = FetchType.EAGER)
    private Set<BibitaOrdine> bibitaOrdine;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "utente")
    private Utente utente;

    @Column(name = "data",nullable = false)
    private LocalDateTime data;


    @Override
    public String toString() {
        return "Ordine{" +"\n"+
                "Id=" + Id +"\n"+
                ", prezzo=" + prezzo +"\n"+
                ", pizzaOrdine=" + pizzaOrdine.toString() +"\n"+
                ", bibitaOrdine=" + bibitaOrdine.toString() +"\n"+
                ", utente=" + utente.toString() +"\n"+
                ", data=" + data +
                '}';
    }
}

