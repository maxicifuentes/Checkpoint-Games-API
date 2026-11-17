package cl.duoc.checkpoint.service;

import cl.duoc.checkpoint.dto.ProductDtos.*;
import cl.duoc.checkpoint.model.*;
import cl.duoc.checkpoint.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
 private final ProductoRepository products; private final CategoriaRepository categories;
 public ProductoService(ProductoRepository products, CategoriaRepository categories) { this.products = products; this.categories = categories; }
 public List<ProductResponse> findAll() { return products.findAll().stream().map(this::map).toList(); }
 public ProductResponse find(Long id) { return map(entity(id)); }
 public ProductResponse create(ProductRequest req) { if (products.findByCodigoIgnoreCase(req.codigo()).isPresent()) throw new IllegalStateException("El código de producto ya existe"); Producto product = new Producto(); apply(product, req); return map(products.save(product)); }
 public ProductResponse update(Long id, ProductRequest req) { Producto product = entity(id); products.findByCodigoIgnoreCase(req.codigo()).filter(p -> !p.getId().equals(id)).ifPresent(p -> { throw new IllegalStateException("El código de producto ya existe"); }); apply(product, req); return map(products.save(product)); }
 public void delete(Long id) { products.delete(entity(id)); }
 private Producto entity(Long id) { return products.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado")); }
 private void apply(Producto p, ProductRequest r) { p.setCodigo(r.codigo().trim().toUpperCase()); p.setNombre(r.nombre()); p.setDescripcion(r.descripcion()); p.setPrecio(r.precio()); p.setPrecioAnterior(r.precioAnterior()); p.setStock(r.stock()); p.setStockCritico(r.stockCritico()); p.setPlataforma(r.plataforma()); p.setColor(r.color() == null ? "#7957ff" : r.color()); p.setRating(r.rating() == null ? 4.5 : r.rating()); p.setCategoria(categories.findByNombreIgnoreCase(r.categoria()).orElseGet(() -> { Categoria c = new Categoria(); c.setNombre(r.categoria()); return categories.save(c); })); }
 private ProductResponse map(Producto p) { return new ProductResponse(p.getId(),p.getCodigo(),p.getNombre(),p.getDescripcion(),p.getPrecio(),p.getPrecioAnterior(),p.getStock(),p.getStockCritico(),p.getCategoria().getNombre(),p.getPlataforma(),p.getColor(),p.getRating()); }
}
