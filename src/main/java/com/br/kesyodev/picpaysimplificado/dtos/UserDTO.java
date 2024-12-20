package com.br.kesyodev.picpaysimplificado.dtos;

import com.br.kesyodev.picpaysimplificado.domain.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String cpf, BigDecimal balance, String email, String password, UserType userType) {
}
