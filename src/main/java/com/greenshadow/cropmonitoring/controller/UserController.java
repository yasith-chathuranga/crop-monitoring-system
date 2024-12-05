package com.greenshadow.cropmonitoring.controller;

import com.greenshadow.cropmonitoring.auth.request.SignInRequest;
import com.greenshadow.cropmonitoring.auth.request.SignUpRequest;
import com.greenshadow.cropmonitoring.auth.response.JWTAuthResponse;
import com.greenshadow.cropmonitoring.dto.impl.UserDTO;
import com.greenshadow.cropmonitoring.exception.AlreadyExistsException;
import com.greenshadow.cropmonitoring.exception.DataPersistFailedException;
import com.greenshadow.cropmonitoring.exception.UserNotFoundException;
import com.greenshadow.cropmonitoring.service.AuthenticationService;
import com.greenshadow.cropmonitoring.service.UserService;
import com.greenshadow.cropmonitoring.util.Mapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignInRequest signInRequest){
        logger.info("SignIn request: {}", signInRequest);
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<JWTAuthResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        logger.info("SignUp request: {}", signUpRequest);
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/check_credentials")
    public Boolean checkCredentials(@RequestBody UserDTO userDTO){
        System.out.println(userDTO.getEmail() + " " + userDTO.getPassword() + " " + userDTO.getRole());
        logger.info("Received request to check credentials for user: {}", userDTO.getEmail());
        return authenticationService.checkCredentials(userDTO);
    }
}
