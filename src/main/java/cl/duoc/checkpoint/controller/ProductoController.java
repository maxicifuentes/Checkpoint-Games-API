package cl.duoc.checkpoint.controller;
import cl.duoc.checkpoint.dto.ProductDtos.*;
import cl.duoc.checkpoint.service.ProductoService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/productos") @Tag(name="Productos")
public class ProductoController { private final ProductoService service; public ProductoController(ProductoService service){this.service=service;}
 @GetMapping @Operation(summary="Lista productos públicos") List<ProductResponse> all(){return service.findAll();}
 @GetMapping("/{id}") ProductResponse one(@PathVariable Long id){return service.find(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary="Crea un producto. Solo Administrador") ProductResponse create(@Valid @RequestBody ProductRequest req){return service.create(req);}
 @PutMapping("/{id}") @Operation(summary="Actualiza un producto. Solo Administrador") ProductResponse update(@PathVariable Long id,@Valid @RequestBody ProductRequest req){return service.update(id,req);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary="Elimina un producto. Solo Administrador") void delete(@PathVariable Long id){service.delete(id);}
}
