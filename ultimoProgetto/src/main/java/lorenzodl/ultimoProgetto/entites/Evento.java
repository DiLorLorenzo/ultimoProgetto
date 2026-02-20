package lorenzodl.ultimoProgetto.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventi")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false, length = 4000)
    private String descrizione;

    @Column(nullable = false)
    private LocalDateTime dataOra;

    @Column(nullable = false)
    private String luogo;

    @Column(nullable = false)
    private Integer postiDisponibili;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizzatore_id", nullable = false)
    private User organizzatore;

    @OneToMany(mappedBy = "evento")
    private List<Prenotazione> prenotazioni;
}