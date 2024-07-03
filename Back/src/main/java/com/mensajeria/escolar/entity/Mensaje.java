package com.mensajeria.escolar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private Long expiration;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "mensaje_curso", joinColumns = @JoinColumn(name = "mensaje_id", referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "curso_id", referencedColumnName = "id")
    )
    private List<Curso> cursos;
}
