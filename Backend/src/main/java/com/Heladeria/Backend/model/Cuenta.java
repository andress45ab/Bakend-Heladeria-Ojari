package com.Heladeria.Backend.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cuentas", uniqueConstraints = @UniqueConstraint(columnNames = "usuario"))
@Data // Sustituye getters y setters
@NoArgsConstructor // Sustituye el constructor vacio
public class Cuenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Column(name = "usuario", nullable = false, unique = true, length = 100)
    private String usuario; // Usado como Email/Username

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Column(nullable = false, length = 255) // Longitud necesaria para el hash de BCrypt
    private String contrasena; // El atributo debe coincidir con el getter/setter si no usas Lombok

    @Enumerated(EnumType.STRING)
     @Column(name = "rol", nullable = false)
     private Rol rol; // <-- Tipo Rol (un solo valor)

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
   
}
