package com.mensajeria.escolar.dto;

import com.mensajeria.escolar.entity.Mensaje;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageResponseDto implements Serializable {
    public String message;
    public Long expiration;

    public MessageResponseDto(Mensaje message) {
        this.message = message.getMensaje();
        this.expiration = message.getExpiration();
    }
}
