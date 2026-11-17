package cl.duoc.checkpoint.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "boletas")
public class Boleta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "usuario_id") private Usuario usuario;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private EstadoBoleta estado = EstadoBoleta.PAGADA;
    @Column(nullable = false) private LocalDateTime fecha = LocalDateTime.now();
    @Column(nullable = false) private BigDecimal total;
    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, orphanRemoval = true) private List<DetalleBoleta> detalles = new ArrayList<>();
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; } public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public EstadoBoleta getEstado() { return estado; } public void setEstado(EstadoBoleta estado) { this.estado = estado; }
    public LocalDateTime getFecha() { return fecha; } public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public BigDecimal getTotal() { return total; } public void setTotal(BigDecimal total) { this.total = total; }
    public List<DetalleBoleta> getDetalles() { return detalles; } public void setDetalles(List<DetalleBoleta> detalles) { this.detalles = detalles; }
}
