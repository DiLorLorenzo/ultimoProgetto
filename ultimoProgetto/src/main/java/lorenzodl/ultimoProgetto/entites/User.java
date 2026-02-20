package lorenzodl.ultimoProgetto.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(nullable = false)
    private String password; // hash BCrypt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private Ruolo ruolo;

    // Eventi creati dall'organizzatore
    // mappedBy deve essere il nome ESATTO del campo in Evento
    @OneToMany(mappedBy = "organizzatore")
    private List<Evento> eventiCreati;

    // Prenotazioni dell’utente
    // mappedBy deve essere il nome ESATTO del campo in Prenotazione
    @OneToMany(mappedBy = "utente")
    private List<Prenotazione> prenotazioni;
}