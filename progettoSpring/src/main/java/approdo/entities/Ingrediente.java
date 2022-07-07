package approdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="ingredienti")
public class Ingrediente {

    @Id
    @Column(name ="nome",nullable = false)
    private String nome;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinTable(name = "composizione",joinColumns = @JoinColumn(name = "ingrediente"),inverseJoinColumns = @JoinColumn(name = "pizza"))
    private Set<Pizza> pizze;

    public String toString(){
        return nome;
    }
}
