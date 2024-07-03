package com.mensajeria.escolar.dto;

import com.mensajeria.escolar.entity.Anio;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnioResponseDto implements Serializable {

    public String anio;
    public List<CursoResponseDto> cursos;

    public AnioResponseDto(Anio anio) {
        this.anio = anio.getAnio();
        this.cursos = anio.getCurso().stream().map(CursoResponseDto::new).toList();
    }
}
