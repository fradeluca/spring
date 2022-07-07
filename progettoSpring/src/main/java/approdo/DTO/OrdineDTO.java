package approdo.DTO;

import approdo.entities.Utente;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter

public class OrdineDTO implements Serializable {

    Utente utente;
    String pizzaOrdine;
    String bibitaOrdine;
    String data;
    double prezzo;

    public OrdineDTO(Utente utente, double prezzo, String pizzaOrdine, String bibitaOrdine,LocalDateTime data) {
        this.utente = utente;
        this.prezzo = prezzo;
        this.pizzaOrdine = pizzaOrdine;
        this.bibitaOrdine = bibitaOrdine;
        this.data=data.toString().replace("T"," ");
    }

    public OrdineDTO(){}

    @Override
    public String toString() {
        return "\n\n\n\nOrdineDTO{" +
                "utente=" + utente.toString() +
                ", pizzaOrdine='" + pizzaOrdine + '\'' +
                ", bibitaOrdine='" + bibitaOrdine + '\'' +
                ", data=" + data +
                ", prezzo=" + prezzo +
                "}\n\n\n\n";
    }
}
