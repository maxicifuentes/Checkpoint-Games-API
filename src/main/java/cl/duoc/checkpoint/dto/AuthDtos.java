package cl.duoc.checkpoint.dto;

import cl.duoc.checkpoint.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public final class AuthDtos {
    private AuthDtos() { }
    public record LoginRequest(@Email @NotBlank String correo, @NotBlank String clave) { }
    public record RegisterRequest(@NotBlank String nombre, @NotBlank String apellidos, @Email @NotBlank String correo, @NotBlank String clave) { }
    public record AuthResponse(String token, String tipo, Long id, String nombre, String correo) { }
    public record UserResponse(Long id, String nombre, String apellidos, String correo, Rol rol, String region, String comuna, String direccion) { }
}
