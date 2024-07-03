package com.mensajeria.escolar.security.service.Impl;

import com.mensajeria.escolar.security.dto.UserRequestDtoUpdate;
import com.mensajeria.escolar.security.dto.UserResponseDto;
import com.mensajeria.escolar.security.exception.ResourceNotFoundException;
import com.mensajeria.escolar.security.mapper.UserMapper;
import com.mensajeria.escolar.security.repository.UserRepository;
import com.mensajeria.escolar.security.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getAll() {
        return userMapper.convertToListDto(userRepository.findAll());
    }

    @Override
    public Optional<UserResponseDto> getUser(Long idUser) {
        return Optional.of(userMapper.toUserDto(userRepository.getUserById(idUser)));
    }

    @Override
    public UserResponseDto update(UserRequestDtoUpdate userDtoUpdate) {
        if(userRepository.findByEmail(userDtoUpdate.getEmail()).isEmpty()){
            throw new ResourceNotFoundException("El correo electrónico no se encuentra registrado");
        }

        var user = userRepository.getUserById(userDtoUpdate.getId());
        BeanUtils.copyProperties(userDtoUpdate, user);

        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public boolean delete(Long idUser) {
        if(userRepository.getUserById(idUser) != null){
            return false;
        }
        userRepository.deleteById(idUser);
        return true;
    }
}
