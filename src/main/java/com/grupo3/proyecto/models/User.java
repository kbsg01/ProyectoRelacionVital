package com.grupo3.proyecto.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 5, message = "Nombre demasiado corto, 5 caracteres minimo")
    private String name;

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El email debe ser valido, ej. 'usuario@example.com'")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    private String password;
    
    @Transient
    private String passwordConfirmation;

    private String imgRoute;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    // Relacion uno a muchos
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<Bitacora> bitacoras;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_medicamentos",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="medicamento_id")
    )
    private List<Medicamento> medicamentos;

    @OneToMany(mappedBy = "user", fetch= FetchType.LAZY)
    private List<UserMedicamento> uMedicamentos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_emociones",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="emocion_id")
    )
    private List<Emocion> emociones;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<UserEmocion> uEmociones;


}
