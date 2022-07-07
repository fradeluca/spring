package approdo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtenteDTO {

    private int id;
    private String nome;
    private String cognome;
    private String password;
    private String phone;
    private String email;
    boolean utente;

    public boolean isValid(){
        if(nome==null||nome=="")
            return false;
        if(cognome==null||cognome=="")
            return false;
        if(phone==null||phone=="")
            return false;
        if(email==null||email=="")
            return false;
        if(!utente)
            return false;
        return true;

    }
}
