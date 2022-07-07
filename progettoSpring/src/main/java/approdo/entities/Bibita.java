package approdo.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Entity
@Table(name = "bibita")
public class Bibita {

    @Id
    @Column(name="nome",nullable = false)
    private String nome;

    @ToString.Exclude
    @Column(name ="prezzo",nullable = false)
    private double prezzo;

}
