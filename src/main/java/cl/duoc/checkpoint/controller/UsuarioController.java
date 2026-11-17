package cl.duoc.checkpoint.controller;
import cl.duoc.checkpoint.dto.AuthDtos.UserResponse;
import cl.duoc.checkpoint.model.Usuario;
import cl.duoc.checkpoint.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/usuarios") @Tag(name="Usuarios")
public class UsuarioController {
 private final UsuarioRepository users; public UsuarioController(UsuarioRepository users){this.users=users;}
 @GetMapping @Operation(summary="Lista usuarios. Solo Administrador") List<UserResponse> all(){return users.findAll().stream().map(this::map).toList();}
 @GetMapping("/{id}") UserResponse one(@PathVariable Long id){return map(users.findById(id).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado")));}
 private UserResponse map(Usuario u){return new UserResponse(u.getId(),u.getNombre(),u.getApellidos(),u.getCorreo(),u.getRol(),u.getRegion(),u.getComuna(),u.getDireccion());}
}
