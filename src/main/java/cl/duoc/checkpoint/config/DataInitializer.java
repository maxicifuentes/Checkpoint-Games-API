package cl.duoc.checkpoint.config;
import cl.duoc.checkpoint.model.*;
import cl.duoc.checkpoint.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Configuration
public class DataInitializer {
 @Bean CommandLineRunner seed(CategoriaRepository cats, ProductoRepository products, UsuarioRepository users, BoletaRepository boletas, PasswordEncoder encoder) { return args -> {
  if(users.count()==0) {
   Usuario a=user("Ada","Lovelace","admin@duoc.cl","admin1",Rol.ADMINISTRADOR,encoder); Usuario v=user("Diego","Rojas Soto","vendedor@duoc.cl","venta1",Rol.VENDEDOR,encoder); Usuario c=user("Camila","Pérez Díaz","cliente@gmail.com","user1",Rol.CLIENTE,encoder); users.save(a);users.save(v);users.save(c);
   for(String name:new String[]{"Acción","Aventura","RPG","Deportes","Carreras","Estrategia","Terror","Indie"}){Categoria cat=new Categoria();cat.setNombre(name);cats.save(cat);}
   add(products,cats,"NEBULA","Nebula Raiders","Acción",39990,18,"PC / PS5","#7957ff"); add(products,cats,"PIXEL","Pixel Valley","Indie",15990,26,"PC / Switch","#23b58b"); add(products,cats,"APEX","Apex Circuit","Carreras",29990,9,"PC / Xbox","#f05d73"); add(products,cats,"KINGDOM","Kingdoms Reborn","RPG",44990,12,"PC / PS5","#d58b3a");
  }
  if(boletas.count()==0 && products.count()>0) {
   Usuario cliente=users.findByCorreoIgnoreCase("cliente@gmail.com").orElseThrow(); var list=products.findAll();
   sample(boletas,cliente,list.get(0),1,EstadoBoleta.PAGADA,LocalDateTime.now().minusDays(3));
   sample(boletas,cliente,list.get(1),2,EstadoBoleta.PREPARANDO,LocalDateTime.now().minusDays(2));
   sample(boletas,cliente,list.get(2),1,EstadoBoleta.ENVIADA,LocalDateTime.now().minusDays(1));
  }
 }; }
 private Usuario user(String n,String a,String mail,String pass,Rol role,PasswordEncoder enc){Usuario u=new Usuario();u.setNombre(n);u.setApellidos(a);u.setCorreo(mail);u.setClave(enc.encode(pass));u.setRol(role);return u;}
 private void add(ProductoRepository p,CategoriaRepository c,String code,String name,String category,int price,int stock,String platform,String color){Producto x=new Producto();x.setCodigo(code);x.setNombre(name);x.setDescripcion("Videojuego disponible en Checkpoint Games.");x.setPrecio(BigDecimal.valueOf(price));x.setStock(stock);x.setStockCritico(4);x.setPlataforma(platform);x.setColor(color);x.setCategoria(c.findByNombreIgnoreCase(category).orElseThrow());p.save(x);}
 private void sample(BoletaRepository boletas,Usuario cliente,Producto producto,int cantidad,EstadoBoleta estado,LocalDateTime fecha){Boleta b=new Boleta();b.setUsuario(cliente);b.setEstado(estado);b.setFecha(fecha);BigDecimal subtotal=producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));b.setTotal(subtotal);DetalleBoleta d=new DetalleBoleta();d.setBoleta(b);d.setProducto(producto);d.setCantidad(cantidad);d.setPrecioUnitario(producto.getPrecio());d.setSubtotal(subtotal);b.getDetalles().add(d);boletas.save(b);}
}
