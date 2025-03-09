package com.example.JobSeekerPortal.controller;

import com.example.JobSeekerPortal.dto.EmailRecepientDTO;
import com.example.JobSeekerPortal.dto.UserDTO;
import com.example.JobSeekerPortal.entity.User;
import com.example.JobSeekerPortal.enums.UserRoles;
import com.example.JobSeekerPortal.service.UserService;
import com.example.JobSeekerPortal.service.EmailService;
import com.example.JobSeekerPortal.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmailService emailService;

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        EmailRecepientDTO emailRecepientDTO = userService.addUser(userDTO);
        if(userDTO.getRoles().contains(UserRoles.ROLE_ADMIN)) {
            emailService.sendAdminVerificationEmail(userDTO,emailRecepientDTO.getToken());
        }else{
            emailService.sendVerificationEmail(emailRecepientDTO.getEmail(),emailRecepientDTO.getToken());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        Optional<User> user = userService.verifyToken(token);

        if (user.isPresent()) {
            return ResponseEntity.ok("User successfully verified!");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if(userService.isUserEnabledByUsername(username)){
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            } catch (Exception e){
                return Map.of("error", "Invalid username or password");
            }

            UserDetails userDetails = userService.loadUserByUsername(username);
            String token = jwtUtil.generateToken(userDetails);

            return Map.of("token", token);
        }
        return Map.of("error", "Your user is not verified, check your email");
    }
}
