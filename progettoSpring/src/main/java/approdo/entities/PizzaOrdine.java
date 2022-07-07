package approdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "pizzaordine")
public class PizzaOrdine {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name="pizza",nullable = false)
    private Pizza pizza;

    @Column(nullable = false)
    private int quantita;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="ordine")
    private Ordine ordine;

}
