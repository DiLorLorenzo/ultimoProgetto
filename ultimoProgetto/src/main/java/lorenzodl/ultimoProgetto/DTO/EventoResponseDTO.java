package lorenzodl.ultimoProgetto.DTO;

import java.time.LocalDateTime;

public record EventoResponseDTO(
        Long id,
        String titolo,
        String descrizione,
        LocalDateTime dataOra,
        String luogo,
        Integer postiDisponibili,
        String emailOrganizzatore
) {}