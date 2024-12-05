package com.greenshadow.cropmonitoring.auth.request;

import com.greenshadow.cropmonitoring.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpRequest {
    private String email;
    private String password;
    private Role role;
}
