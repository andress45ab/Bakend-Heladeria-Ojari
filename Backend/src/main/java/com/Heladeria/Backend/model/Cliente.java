package com.Heladeria.Backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column; // Importa todas las clases de JPA
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Importa Data de Lombok
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera el constructor vacio requerido por JPA
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Siempre usa Long para IDs

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombre;

    @Column(length = 50)
    @Size(max = 50, message = "El apellido no debe exceder los 50 caracteres.")
    private String apellido;

    @Column(length = 20)
    @Size(max = 20, message = "El teléfono no debe exceder los 20 caracteres.")
    private String telefono;

    @Column(length = 255)
    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres.")
    private String direccion;
    
    @Column(nullable = false, length = 50)
    @NotBlank(message = "El email no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El email debe tener entre 2 y 50 caracteres.")
    private String email;

    // Nueva Relación: Un empleado tiene una única cuenta de acceso (usuario, contraseña, roles).
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cuenta_id", referencedColumnName = "id")
    private Cuenta cuenta;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    } 
   
    
}
