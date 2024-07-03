package com.mensajeria.escolar.security.service;

import com.mensajeria.escolar.security.dto.UserRequestDtoUpdate;
import com.mensajeria.escolar.security.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserResponseDto> getAll();
    Optional<UserResponseDto> getUser(Long idUser);
    UserResponseDto update(UserRequestDtoUpdate userDtoUpdate);
    boolean delete(Long idUser);
}
