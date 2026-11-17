package cl.duoc.checkpoint.dto;

import cl.duoc.checkpoint.model.EstadoBoleta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public final class OrderDtos {
    private OrderDtos() { }
    public record OrderLineRequest(@NotNull Long productoId, @NotNull @Min(1) Integer cantidad) { }
    public record CreateOrderRequest(@NotEmpty List<OrderLineRequest> items) { }
    public record OrderLineResponse(Long productoId, String producto, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) { }
    public record OrderResponse(Long id, Long usuarioId, String cliente, EstadoBoleta estado, LocalDateTime fecha, BigDecimal total, List<OrderLineResponse> detalles) { }
    public record UpdateStatusRequest(@NotNull EstadoBoleta estado) { }
}
