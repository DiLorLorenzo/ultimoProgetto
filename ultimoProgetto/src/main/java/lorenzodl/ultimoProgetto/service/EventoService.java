package lorenzodl.ultimoProgetto.service;
import lorenzodl.ultimoProgetto.DTO.EventoRequestDTO;
import lorenzodl.ultimoProgetto.DTO.EventoResponseDTO;
import lorenzodl.ultimoProgetto.entites.Evento;
import lorenzodl.ultimoProgetto.entites.User;
import lorenzodl.ultimoProgetto.exceptions.BadRequestException;
import lorenzodl.ultimoProgetto.Repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }


    public EventoResponseDTO creaEvento(EventoRequestDTO dto, User organizzatore) {

        if (dto.postiDisponibili() == null || dto.postiDisponibili() <= 0) {
            throw new BadRequestException("postiDisponibili deve essere > 0");
        }

        Evento e = new Evento();
        e.setTitolo(dto.titolo());
        e.setDescrizione(dto.descrizione());
        e.setDataOra(dto.dataOra());
        e.setLuogo(dto.luogo());
        e.setPostiDisponibili(dto.postiDisponibili());
        e.setOrganizzatore(organizzatore);

        Evento salvato = eventoRepository.save(e);

        return new EventoResponseDTO(
                salvato.getId(),
                salvato.getTitolo(),
                salvato.getDescrizione(),
                salvato.getDataOra(),
                salvato.getLuogo(),
                salvato.getPostiDisponibili(),
                salvato.getOrganizzatore().getEmail()
        );
    }


    public List<EventoResponseDTO> listaEventi() {
        return eventoRepository.findAll().stream().map(e ->
                new EventoResponseDTO(
                        e.getId(),
                        e.getTitolo(),
                        e.getDescrizione(),
                        e.getDataOra(),
                        e.getLuogo(),
                        e.getPostiDisponibili(),
                        e.getOrganizzatore().getEmail()
                )
        ).toList();
    }




}