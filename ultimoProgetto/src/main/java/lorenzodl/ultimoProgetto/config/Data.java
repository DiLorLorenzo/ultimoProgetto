package lorenzodl.ultimoProgetto.config;
import lorenzodl.ultimoProgetto.entites.Evento;
import lorenzodl.ultimoProgetto.entites.Ruolo;
import lorenzodl.ultimoProgetto.entites.User;
import lorenzodl.ultimoProgetto.Repository.EventoRepository;
import lorenzodl.ultimoProgetto.Repository.PrenotazioneRepository;
import lorenzodl.ultimoProgetto.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class Data {

    @Bean
    CommandLineRunner Datainit(
            UserRepository userRepo,
            EventoRepository eventoRepo,
            PrenotazioneRepository prenotazioneRepo,
            PasswordEncoder encoder
    ) {
        return args -> {


            if (userRepo.count() == 0) {

                //  ORGANIZZATORE
                User organizzatore = new User();
                organizzatore.setEmail("org1@mail.com");
                organizzatore.setPassword(encoder.encode("Password123!"));
                organizzatore.setRuolo(Ruolo.ORGANIZZATORE_EVENTO);
                userRepo.save(organizzatore);

                //  UTENTE NORMALE
                User utente = new User();
                utente.setEmail("user1@mail.com");
                utente.setPassword(encoder.encode("Password123!"));
                utente.setRuolo(Ruolo.UTENTE);
                userRepo.save(utente);

                //  EVENTI
                Evento evento1 = new Evento();
                evento1.setTitolo("Concerto Rock");
                evento1.setDescrizione("Serata live");
                evento1.setDataOra(LocalDateTime.of(2026, 3, 10, 21, 0));
                evento1.setLuogo("Milano");
                evento1.setPostiDisponibili(50);
                evento1.setOrganizzatore(organizzatore);
                eventoRepo.save(evento1);

                Evento evento2 = new Evento();
                evento2.setTitolo("Workshop JWT");
                evento2.setDescrizione("Spring Security + JWT");
                evento2.setDataOra(LocalDateTime.of(2026, 3, 12, 18, 0));
                evento2.setLuogo("Roma");
                evento2.setPostiDisponibili(30);
                evento2.setOrganizzatore(organizzatore);
                eventoRepo.save(evento2);


                eventoRepo.save(evento1);



                System.out.println("DB popolato  (users + eventi) ");
            }
        };
    }
}