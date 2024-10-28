package com.emadSolutions.workout_tracker.auth;


import com.emadSolutions.workout_tracker.DTOs.SuccessResponse;
import com.emadSolutions.workout_tracker.DTOs.UserDataDTO;
import com.emadSolutions.workout_tracker.DTOs.UserLogin;
import com.emadSolutions.workout_tracker.user.User;
import com.emadSolutions.workout_tracker.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user)
    {
        SuccessResponse<UserDataDTO> response = new SuccessResponse<UserDataDTO>(
                SuccessResponse.ResponseStatus.success
                ,"User Created Successfully"
        );
        response.setData(authService.registerUser(user));

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLogin userLoginData)
    {
        var tokenDetails = authService.handleLogin(userLoginData);

        SuccessResponse<Map<String,Object>> response = new SuccessResponse<>(
                SuccessResponse.ResponseStatus.success,
                "logged in successfully"
        );
        response.setData(tokenDetails);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
