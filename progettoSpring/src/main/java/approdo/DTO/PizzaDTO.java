package approdo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PizzaDTO implements Serializable {


    private String nome;

    private double prezzo;

    private String ingredienti;

    @Override
    public String toString() {
        return "PizzaDTO{" +
                "nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", ingredienti='" + ingredienti + '\'' +
                '}';
    }
}
