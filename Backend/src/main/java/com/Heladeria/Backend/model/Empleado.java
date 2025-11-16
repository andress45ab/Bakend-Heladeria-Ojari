package com.Heladeria.Backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data; 
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados") 
@Data // Sustituye getters y setters
@NoArgsConstructor // Sustituye el constructor vacio
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50) // Refleja la validacion en la DB
    @NotBlank(message = "El nombre del empleado no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombre;
    
    // Nueva Relación: Un empleado tiene una única cuenta de acceso (usuario, contraseña, roles).
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cuenta_id", referencedColumnName = "id")
    private Cuenta cuenta; 

    @Column(nullable = false, length = 50) // Refleja la validacion en la DB
    @NotBlank(message = "El apellido del empleado no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres.")
    private String apellido;
    
    @Column(nullable = false, length = 15) // Refleja la validacion en la DB
    @NotBlank(message = "El telefono del empleado no puede estar vacío.")
    @Size(min = 7, max = 15, message = "El telefono debe tener entre 7 y 15 caracteres.")
    private String telefono;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    
    
    // ---------------------------------------------
}