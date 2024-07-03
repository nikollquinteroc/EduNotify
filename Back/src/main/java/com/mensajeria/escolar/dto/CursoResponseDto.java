package com.mensajeria.escolar.dto;

import com.mensajeria.escolar.entity.Curso;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CursoResponseDto implements Serializable {
    public String curso;

    public CursoResponseDto(Curso curso) {
        this.curso = curso.getCurso();
    }
}
