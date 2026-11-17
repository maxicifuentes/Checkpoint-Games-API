package cl.duoc.checkpoint.repository;
import cl.duoc.checkpoint.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CategoriaRepository extends JpaRepository<Categoria, Long> { Optional<Categoria> findByNombreIgnoreCase(String nombre); }
