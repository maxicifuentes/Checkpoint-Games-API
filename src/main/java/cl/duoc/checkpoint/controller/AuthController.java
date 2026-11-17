package cl.duoc.checkpoint.controller;
import cl.duoc.checkpoint.dto.AuthDtos.*;
import cl.duoc.checkpoint.service.AuthService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/auth") @Tag(name="Autenticación")
public class AuthController { private final AuthService service; public AuthController(AuthService service){this.service=service;}
 @PostMapping("/login") @Operation(summary="Inicia sesión y entrega un JWT") AuthResponse login(@Valid @RequestBody LoginRequest request){return service.login(request);}
 @PostMapping("/registro") @ResponseStatus(HttpStatus.CREATED) @Operation(summary="Registra un cliente") AuthResponse register(@Valid @RequestBody RegisterRequest request){return service.register(request);}
 @PostMapping("/refresh") @Operation(summary="Renueva un JWT válido") AuthResponse refresh(Authentication authentication){return service.refresh(authentication.getName());}
}
