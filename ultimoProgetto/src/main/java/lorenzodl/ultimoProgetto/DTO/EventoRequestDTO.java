package lorenzodl.ultimoProgetto.DTO;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record EventoRequestDTO(
        @NotBlank String titolo,
        @NotBlank String descrizione,
        @NotNull LocalDateTime dataOra,
        @NotBlank String luogo,
        @NotNull @Min(1) Integer postiDisponibili
) {}