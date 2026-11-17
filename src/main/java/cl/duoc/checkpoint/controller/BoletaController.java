package cl.duoc.checkpoint.controller;
import cl.duoc.checkpoint.dto.OrderDtos.*;
import cl.duoc.checkpoint.repository.UsuarioRepository;
import cl.duoc.checkpoint.service.BoletaService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/boletas") @Tag(name="Boletas y compras")
public class BoletaController { private final BoletaService service; private final UsuarioRepository users; public BoletaController(BoletaService service,UsuarioRepository users){this.service=service;this.users=users;}
 @GetMapping @Operation(summary="Lista boletas. Cliente solo ve las propias") List<OrderResponse> all(Authentication auth){var u=users.findByCorreoIgnoreCase(auth.getName()).orElseThrow(); return u.getRol().name().equals("CLIENTE")?service.userOrders(u.getId()):service.all();}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary="Crea una compra. Solo Cliente") OrderResponse create(Authentication auth,@Valid @RequestBody CreateOrderRequest req){return service.create(users.findByCorreoIgnoreCase(auth.getName()).orElseThrow().getId(),req);}
 @PatchMapping("/{id}/estado") @Operation(summary="Actualiza el estado. Solo Administrador") OrderResponse status(@PathVariable Long id,@Valid @RequestBody UpdateStatusRequest req){return service.updateStatus(id,req);}
}
