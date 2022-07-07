package approdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "user")
public class Utente {

    @Id
    @Column(name="id",nullable = false)
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int Id;


    @Column(unique = true,nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true,nullable = false)
    private String email;


    @Column(nullable = false)
    private boolean utente;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "utente")
    private Set<Ordine> ordini;


}
