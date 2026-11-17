package cl.duoc.checkpoint.security;

import cl.duoc.checkpoint.repository.UsuarioRepository;
import cl.duoc.checkpoint.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwt; private final UsuarioRepository users;
    public JwtAuthenticationFilter(JwtService jwt, UsuarioRepository users) { this.jwt = jwt; this.users = users; }
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                var claims = jwt.claims(header.substring(7));
                users.findByCorreoIgnoreCase(claims.getSubject()).ifPresent(user -> {
                    var auth = new UsernamePasswordAuthenticationToken(user.getCorreo(), null, List.of(new SimpleGrantedAuthority("ROLE_" + user.getRol().name())));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            } catch (Exception ignored) { SecurityContextHolder.clearContext(); }
        }
        chain.doFilter(request, response);
    }
}
