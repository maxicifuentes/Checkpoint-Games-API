package cl.duoc.checkpoint.controller;
import cl.duoc.checkpoint.dto.AuthDtos.UserResponse;
import cl.duoc.checkpoint.dto.AuthDtos.UserRequest;
import cl.duoc.checkpoint.model.Usuario;
import cl.duoc.checkpoint.model.Rol;
import cl.duoc.checkpoint.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;
import java.util.List;
@RestController @RequestMapping("/api/v1/usuarios") @Tag(name="Usuarios")
public class UsuarioController {
 private final UsuarioRepository users; private final PasswordEncoder encoder;
 public UsuarioController(UsuarioRepository users, PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 @GetMapping @Operation(summary="Lista usuarios. Solo Administrador") List<UserResponse> all(){return users.findAll().stream().map(this::map).toList();}
 @GetMapping("/{id}") UserResponse one(@PathVariable Long id){return map(users.findById(id).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado")));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary="Crea un usuario. Solo Administrador") UserResponse create(@Valid @RequestBody UserRequest request){
  if(users.existsByCorreoIgnoreCase(request.correo())) throw new IllegalStateException("Este correo ya está registrado");
  if(request.clave()==null || request.clave().length()<4) throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres");
  Usuario user=new Usuario(); apply(user,request,true); return map(users.save(user));
 }
 @PutMapping("/{id}") @Operation(summary="Actualiza un usuario. Solo Administrador") UserResponse update(@PathVariable Long id,@Valid @RequestBody UserRequest request){
  Usuario user=users.findById(id).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado"));
  users.findByCorreoIgnoreCase(request.correo()).filter(other->!other.getId().equals(id)).ifPresent(other->{throw new IllegalStateException("Este correo ya está registrado");});
  apply(user,request,false); return map(users.save(user));
 }
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary="Elimina un usuario. Solo Administrador") void delete(@PathVariable Long id){
  Usuario user=users.findById(id).orElseThrow(()->new IllegalArgumentException("Usuario no encontrado")); if(user.getRol()==Rol.ADMINISTRADOR) throw new IllegalStateException("No se puede eliminar una cuenta administradora"); users.delete(user);
 }
 private void apply(Usuario u,UserRequest r,boolean creating){u.setNombre(r.nombre().trim());u.setApellidos(r.apellidos().trim());u.setCorreo(r.correo().trim().toLowerCase());u.setRol(r.rol()==null?Rol.CLIENTE:r.rol());u.setRegion(r.region());u.setComuna(r.comuna());u.setDireccion(r.direccion());if(creating || (r.clave()!=null&&!r.clave().isBlank()))u.setClave(encoder.encode(r.clave()));}
 private UserResponse map(Usuario u){return new UserResponse(u.getId(),u.getNombre(),u.getApellidos(),u.getCorreo(),u.getRol(),u.getRegion(),u.getComuna(),u.getDireccion());}
}
