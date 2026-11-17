package cl.duoc.checkpoint.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "correo"))
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank private String nombre;
    @NotBlank private String apellidos;
    @Email @NotBlank private String correo;
    @JsonIgnore @NotBlank private String clave;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Rol rol = Rol.CLIENTE;
    private String region;
    private String comuna;
    private String direccion;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; } public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; } public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCorreo() { return correo; } public void setCorreo(String correo) { this.correo = correo; }
    public String getClave() { return clave; } public void setClave(String clave) { this.clave = clave; }
    public Rol getRol() { return rol; } public void setRol(Rol rol) { this.rol = rol; }
    public String getRegion() { return region; } public void setRegion(String region) { this.region = region; }
    public String getComuna() { return comuna; } public void setComuna(String comuna) { this.comuna = comuna; }
    public String getDireccion() { return direccion; } public void setDireccion(String direccion) { this.direccion = direccion; }
}
