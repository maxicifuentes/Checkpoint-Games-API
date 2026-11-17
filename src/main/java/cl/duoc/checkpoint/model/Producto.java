package cl.duoc.checkpoint.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos", uniqueConstraints = @UniqueConstraint(columnNames = "codigo"))
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable = false) private String codigo;
    @NotBlank @Column(nullable = false) private String nombre;
    @Column(length = 1000) private String descripcion;
    @NotNull @DecimalMin("0.0") private BigDecimal precio;
    @DecimalMin("0.0") private BigDecimal precioAnterior;
    @NotNull @Min(0) private Integer stock;
    @NotNull @Min(0) private Integer stockCritico = 0;
    private String plataforma;
    private String color = "#7957ff";
    private Double rating = 4.5;
    @ManyToOne(optional = false) @JoinColumn(name = "categoria_id") private Categoria categoria;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; } public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; } public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; } public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; } public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public BigDecimal getPrecioAnterior() { return precioAnterior; } public void setPrecioAnterior(BigDecimal precioAnterior) { this.precioAnterior = precioAnterior; }
    public Integer getStock() { return stock; } public void setStock(Integer stock) { this.stock = stock; }
    public Integer getStockCritico() { return stockCritico; } public void setStockCritico(Integer stockCritico) { this.stockCritico = stockCritico; }
    public String getPlataforma() { return plataforma; } public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public String getColor() { return color; } public void setColor(String color) { this.color = color; }
    public Double getRating() { return rating; } public void setRating(Double rating) { this.rating = rating; }
    public Categoria getCategoria() { return categoria; } public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
