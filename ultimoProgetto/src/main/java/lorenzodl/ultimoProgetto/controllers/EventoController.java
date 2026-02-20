package lorenzodl.ultimoProgetto.controllers;

import lorenzodl.ultimoProgetto.DTO.EventoRequestDTO;
import lorenzodl.ultimoProgetto.DTO.EventoResponseDTO;
import lorenzodl.ultimoProgetto.entites.User;
import lorenzodl.ultimoProgetto.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ORGANIZZATORE_EVENTO')")
    public ResponseEntity<EventoResponseDTO> creaEvento(@RequestBody EventoRequestDTO dto,
                                                        Authentication authentication) {

        User organizzatore = (User) authentication.getPrincipal();
        EventoResponseDTO creato = eventoService.creaEvento(dto, organizzatore);

        return ResponseEntity.status(HttpStatus.CREATED).body(creato);
    }


    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listaEventi() {
        return ResponseEntity.ok(eventoService.listaEventi());
    }


}