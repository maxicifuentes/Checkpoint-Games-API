package cl.duoc.checkpoint.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name = "detalle_boleta")
public class DetalleBoleta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @JsonIgnore @ManyToOne(optional = false) @JoinColumn(name = "boleta_id") private Boleta boleta;
    @ManyToOne(optional = false) @JoinColumn(name = "producto_id") private Producto producto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Boleta getBoleta() { return boleta; } public void setBoleta(Boleta boleta) { this.boleta = boleta; }
    public Producto getProducto() { return producto; } public void setProducto(Producto producto) { this.producto = producto; }
    public Integer getCantidad() { return cantidad; } public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; } public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; } public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
