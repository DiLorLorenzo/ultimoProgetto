package lorenzodl.ultimoProgetto.Repository;


import lorenzodl.ultimoProgetto.entites.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
}