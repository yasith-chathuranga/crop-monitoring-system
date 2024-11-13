package com.greenshadow.cropmonitoring.dto.impl;

import com.greenshadow.cropmonitoring.customObj.UserResponse;
import com.greenshadow.cropmonitoring.dto.SuperDTO;
import com.greenshadow.cropmonitoring.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements SuperDTO, UserResponse {
    private String email;
    private String password;
    private String role;
}
