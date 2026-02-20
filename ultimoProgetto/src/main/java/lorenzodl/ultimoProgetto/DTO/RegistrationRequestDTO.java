package lorenzodl.ultimoProgetto.DTO;

import lorenzodl.ultimoProgetto.entites.Ruolo;

public record RegistrationRequestDTO(String email, String password, Ruolo ruolo) {}