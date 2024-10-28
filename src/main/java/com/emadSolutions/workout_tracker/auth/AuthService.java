package com.emadSolutions.workout_tracker.auth;

import com.emadSolutions.workout_tracker.security.CustomUserDetailsService;
import com.emadSolutions.workout_tracker.DTOs.UserDTOMapper;
import com.emadSolutions.workout_tracker.DTOs.UserDataDTO;
import com.emadSolutions.workout_tracker.DTOs.UserLogin;
import com.emadSolutions.workout_tracker.Exceptions.ConflictException;
import com.emadSolutions.workout_tracker.security.JwtTokenProvider;
import com.emadSolutions.workout_tracker.user.User;
import com.emadSolutions.workout_tracker.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public AuthService(UserService userService, AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Map<String, Object> handleLogin(UserLogin userLoginData) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginData.getUsername(),
                            userLoginData.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginData.getUsername());
                String token = jwtTokenProvider.generateToken(userDetails);
                Date expirationDate = jwtTokenProvider.getExpirationDateFromToken(token);
                Date issuedAt = jwtTokenProvider.getIssuedAtDateFromToken(token);
                long expiresIn = (expirationDate.getTime() - System.currentTimeMillis()) / 1000;
                String username = userDetails.getUsername();

                return getTokenDetails(token,expiresIn,issuedAt,username);
            } else {
                throw new BadCredentialsException("Authentication failed");
            }

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
    private Map<String,Object> getTokenDetails(String token, long expiresIn,Date issuedAt,String username)
    {
        Map<String, Object> tokenMap = new HashMap<>();
        Map<String,String> userMap = new HashMap<>();

        tokenMap.put("token",token);
        tokenMap.put("expiresIn",expiresIn);
        tokenMap.put("issuedAt",issuedAt);
        tokenMap.put("type","Bearer");
        tokenMap.put("user",userMap);
        userMap.put("username", username);

        return tokenMap;
    }
    public UserDataDTO registerUser(User user) {
        Optional<User> foundUser = userService.getUserByUsername(user.getUsername());

        if (foundUser.isPresent())
            throw new ConflictException("Username is already taken");

        user.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userService.savedUser(user);

        return UserDTOMapper.INSTANCE.toDTO(savedUser);
    }
}
