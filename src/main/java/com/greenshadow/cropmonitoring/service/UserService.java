package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    void save(UserDTO userDTO);
}
