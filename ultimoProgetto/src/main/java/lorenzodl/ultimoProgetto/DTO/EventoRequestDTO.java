package lorenzodl.ultimoProgetto.DTO;

import java.time.LocalDateTime;

public record EventoRequestDTO(
        String titolo,
        String descrizione,
        LocalDateTime dataOra,
        String luogo,
        Integer postiDisponibili
) {}