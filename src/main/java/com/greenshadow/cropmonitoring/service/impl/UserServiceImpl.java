package com.greenshadow.cropmonitoring.service.impl;

import com.greenshadow.cropmonitoring.dao.UserDao;
import com.greenshadow.cropmonitoring.dto.impl.UserDTO;
import com.greenshadow.cropmonitoring.entity.impl.UserEntity;
import com.greenshadow.cropmonitoring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final ModelMapper mapper;
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            return userDao.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        };
    }

    @Override
    public void save(UserDTO userDTO) {
        userDao.save(mapper.map(userDTO, UserEntity.class));
    }
}
