package com.mensajeria.escolar.service;

import com.mensajeria.escolar.dto.EscuelaRequestDto;
import com.mensajeria.escolar.dto.EscuelaResponseDto;
import com.mensajeria.escolar.entity.Escuela;

public interface EscuelaService {
    void newEscuela(EscuelaRequestDto schoolDto);

    Escuela verEscuela(Long id);

}
