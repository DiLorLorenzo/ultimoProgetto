package lorenzodl.ultimoProgetto.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lorenzodl.ultimoProgetto.entites.User;
import lorenzodl.ultimoProgetto.exceptions.UnauthorizedException;
import lorenzodl.ultimoProgetto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    private final JWTtools jwtTools;
    private final UserService userService;

    @Autowired
    public JWTCheckerFilter(JWTtools jwtTools, UserService userService) {
        this.jwtTools = jwtTools;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Token mancante o formato non valido");
        }


        String accessToken = authHeader.substring(7);


        jwtTools.verifyToken(accessToken);


        String email = jwtTools.extractEmailFromToken(accessToken);
        User userAutenticato = userService.findByEmail(email);


        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + userAutenticato.getRuolo().name()));

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userAutenticato, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}