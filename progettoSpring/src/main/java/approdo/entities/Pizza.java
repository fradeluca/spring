package approdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name= "pizza")
public class Pizza {

    @Id
    @Column(nullable = false)
    private String nome;

    @ToString.Exclude
    @Column(nullable = false)
    private double prezzo;

    @JsonIgnore
    @Column(name="disponibile")
    private boolean disponibile;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="composizione",joinColumns = @JoinColumn(name = "pizza"),inverseJoinColumns = @JoinColumn(name = "ingrediente"))
    private Set<Ingrediente> ingredienti;

}
