package cl.duoc.checkpoint.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public final class ProductDtos {
    private ProductDtos() { }
    public record ProductRequest(@NotBlank String codigo, @NotBlank String nombre, String descripcion, @NotNull @DecimalMin("0.0") BigDecimal precio, BigDecimal precioAnterior, @NotNull @Min(0) Integer stock, @NotNull @Min(0) Integer stockCritico, @NotBlank String categoria, String plataforma, String color, Double rating) { }
    public record ProductResponse(Long id, String codigo, String nombre, String descripcion, BigDecimal precio, BigDecimal precioAnterior, Integer stock, Integer stockCritico, String categoria, String plataforma, String color, Double rating) { }
}
