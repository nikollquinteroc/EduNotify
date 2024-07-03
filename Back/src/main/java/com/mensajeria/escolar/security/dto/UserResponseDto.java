package com.mensajeria.escolar.security.dto;

import com.mensajeria.escolar.security.entity.RoleName;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private RoleName role;
}
