package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.UserResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import com.greenshadow.cropmonitoring.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO implements SuperDTO, UserResponse {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private Role role;
}
