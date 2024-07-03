package com.mensajeria.escolar.dto;

import com.mensajeria.escolar.entity.NivelEscolar;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelEscolarResponseDto implements Serializable {

    public String nivelEscolar;
    public List<AnioResponseDto> anios;

    public NivelEscolarResponseDto(NivelEscolar nivelEscolar) {
        this.nivelEscolar = nivelEscolar.getNivel().toString();
        this.anios = nivelEscolar.getAnios().stream().map(AnioResponseDto::new).toList();
    }
}
