package com.greenshadow.cropmonitoring.service;

import com.greenshadow.cropmonitoring.auth.request.SignInRequest;
import com.greenshadow.cropmonitoring.auth.request.SignUpRequest;
import com.greenshadow.cropmonitoring.auth.response.JWTAuthResponse;
import com.greenshadow.cropmonitoring.dto.impl.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    JWTAuthResponse signIn(SignInRequest sIgnInRequest);
    JWTAuthResponse signUp(SignUpRequest signUpRequest);

   // List<String> sendWishes();

    Boolean checkCredentials(UserDTO userDTO);
}
