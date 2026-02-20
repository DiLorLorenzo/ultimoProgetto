package lorenzodl.ultimoProgetto.Repository;

import lorenzodl.ultimoProgetto.entites.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {


    List<Evento> findAllByOrganizzatoreEmail(String email);
}