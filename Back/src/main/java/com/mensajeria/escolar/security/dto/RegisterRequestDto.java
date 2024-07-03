package com.mensajeria.escolar.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    private String name;
    private String lastName;
    private String email;
    private String password;
}
