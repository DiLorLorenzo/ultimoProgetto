package lorenzodl.ultimoProgetto.service;
import lorenzodl.ultimoProgetto.DTO.LoginRequestDTO;
import lorenzodl.ultimoProgetto.DTO.LoginResponseDTO;
import lorenzodl.ultimoProgetto.DTO.RegistrationRequestDTO;
import lorenzodl.ultimoProgetto.entites.User;
import lorenzodl.ultimoProgetto.exceptions.ConflictException;
import lorenzodl.ultimoProgetto.exceptions.UnauthorizedException;
import lorenzodl.ultimoProgetto.Repository.UserRepository;
import lorenzodl.ultimoProgetto.security.JWTtools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTtools jwtTools;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JWTtools jwtTools) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTools = jwtTools;
    }


    public void register(RegistrationRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email già registrata");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRuolo(request.ruolo());

        userRepository.save(user);
    }


    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException("Credenziali non valide"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Credenziali non valide");
        }

        String token = jwtTools.generateToken(user);
        return new LoginResponseDTO(token);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Utente non trovato"));
    }
}