package lorenzodl.ultimoProgetto.controllers;
import lorenzodl.ultimoProgetto.DTO.LoginRequestDTO;
import lorenzodl.ultimoProgetto.DTO.LoginResponseDTO;
import lorenzodl.ultimoProgetto.DTO.RegistrationRequestDTO;
import lorenzodl.ultimoProgetto.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequestDTO request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.login(request));
    }
}