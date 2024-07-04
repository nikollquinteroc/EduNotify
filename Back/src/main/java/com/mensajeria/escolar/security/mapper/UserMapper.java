package com.mensajeria.escolar.security.mapper;

import com.mensajeria.escolar.security.dto.UserResponseDto;
import com.mensajeria.escolar.security.entity.User;
import com.mensajeria.escolar.service.EscuelaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final EscuelaService escuelaService;
    public User toUserEntity(UserResponseDto userDto){


        return User.builder()
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .build();
    }

    public UserResponseDto toUserDto(User userEntity){

        return UserResponseDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .school(escuelaService.verEscuela(userEntity.getSchool_id()).getId())
                .build();

    }

    public List<UserResponseDto> convertToListDto(List<User> users){

        if ( users == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<>();
        for ( User c : users ) {
            list.add(toUserDto(c));
        }

        return list;
    }
}
