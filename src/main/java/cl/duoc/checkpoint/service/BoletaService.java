package cl.duoc.checkpoint.service;

import cl.duoc.checkpoint.dto.OrderDtos.*;
import cl.duoc.checkpoint.model.*;
import cl.duoc.checkpoint.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BoletaService {
 private final BoletaRepository bills; private final ProductoRepository products; private final UsuarioRepository users;
 public BoletaService(BoletaRepository bills, ProductoRepository products, UsuarioRepository users) { this.bills=bills; this.products=products; this.users=users; }
 @Transactional(readOnly = true) public List<OrderResponse> all() { return bills.findAll().stream().map(this::map).toList(); }
 @Transactional(readOnly = true) public List<OrderResponse> userOrders(Long userId) { return bills.findByUsuarioId(userId).stream().map(this::map).toList(); }
 @Transactional public OrderResponse create(Long userId, CreateOrderRequest request) {
  Usuario user = users.findById(userId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")); Boleta bill = new Boleta(); bill.setUsuario(user); BigDecimal total=BigDecimal.ZERO;
  for (OrderLineRequest line: request.items()) { Producto p=products.findById(line.productoId()).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado")); if(p.getStock()<line.cantidad()) throw new IllegalStateException("Stock insuficiente para "+p.getNombre()); BigDecimal subtotal=p.getPrecio().multiply(BigDecimal.valueOf(line.cantidad())); DetalleBoleta detail=new DetalleBoleta(); detail.setBoleta(bill); detail.setProducto(p); detail.setCantidad(line.cantidad()); detail.setPrecioUnitario(p.getPrecio()); detail.setSubtotal(subtotal); bill.getDetalles().add(detail); p.setStock(p.getStock()-line.cantidad()); total=total.add(subtotal); }
  bill.setTotal(total); return map(bills.save(bill));
 }
 @Transactional public OrderResponse updateStatus(Long id, UpdateStatusRequest request) { Boleta b=bills.findById(id).orElseThrow(() -> new IllegalArgumentException("Boleta no encontrada")); b.setEstado(request.estado()); return map(b); }
 private OrderResponse map(Boleta b) { var lines=b.getDetalles().stream().map(d -> new OrderLineResponse(d.getProducto().getId(),d.getProducto().getNombre(),d.getCantidad(),d.getPrecioUnitario(),d.getSubtotal())).toList(); return new OrderResponse(b.getId(), b.getUsuario().getId(), b.getUsuario().getNombre()+" "+b.getUsuario().getApellidos(), b.getEstado(),b.getFecha(),b.getTotal(),lines); }
}
