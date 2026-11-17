package cl.duoc.checkpoint.controller;
import cl.duoc.checkpoint.model.Categoria;
import cl.duoc.checkpoint.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/categorias") @Tag(name="Categorías")
public class CategoriaController { private final CategoriaRepository categories; public CategoriaController(CategoriaRepository categories){this.categories=categories;} @GetMapping public List<Categoria> all(){return categories.findAll();} }
