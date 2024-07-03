package com.mensajeria.escolar.dto;

import com.mensajeria.escolar.entity.Escuela;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscuelaResponseDto implements Serializable {

    public String nombre;
    public List<NivelEscolarResponseDto> niveles;

    public EscuelaResponseDto(Escuela escuela) {
        this.nombre = escuela.getNombre();
        this.niveles = escuela.getNiveles().stream().map(NivelEscolarResponseDto::new).toList();
    }
}
