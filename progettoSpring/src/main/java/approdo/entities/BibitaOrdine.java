package approdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Getter
@Setter
@Entity
@ToString
@Table(name = "bibitaordine")
public class BibitaOrdine {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name="bibita")
    private Bibita bibita;

    @JoinColumn(name="quantita")
    private int quantita;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "ordine")
    private Ordine ordine;


}
