package cl.duoc.checkpoint.service;

import cl.duoc.checkpoint.dto.AuthDtos.*;
import cl.duoc.checkpoint.model.*;
import cl.duoc.checkpoint.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
    public AuthService(UsuarioRepository users, PasswordEncoder encoder, JwtService jwt) { this.users = users; this.encoder = encoder; this.jwt = jwt; }
    public AuthResponse login(LoginRequest request) {
        Usuario user = users.findByCorreoIgnoreCase(request.correo()).orElseThrow(() -> new IllegalArgumentException("Correo o contraseña incorrectos"));
        if (!encoder.matches(request.clave(), user.getClave())) throw new IllegalArgumentException("Correo o contraseña incorrectos");
        return response(user);
    }
    public AuthResponse register(RegisterRequest request) {
        if (users.existsByCorreoIgnoreCase(request.correo())) throw new IllegalStateException("El correo ya está registrado");
        Usuario user = new Usuario(); user.setNombre(request.nombre()); user.setApellidos(request.apellidos()); user.setCorreo(request.correo().toLowerCase()); user.setClave(encoder.encode(request.clave())); user.setRol(Rol.CLIENTE);
        return response(users.save(user));
    }
    public AuthResponse refresh(String correo) {
        return response(users.findByCorreoIgnoreCase(correo).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")));
    }
    private AuthResponse response(Usuario u) { return new AuthResponse(jwt.generate(u), u.getRol().name(), u.getId(), u.getNombre(), u.getCorreo()); }
}
