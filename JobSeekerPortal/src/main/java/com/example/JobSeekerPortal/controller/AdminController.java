package com.example.JobSeekerPortal.controller;

import com.example.JobSeekerPortal.dto.UserDTO;
import com.example.JobSeekerPortal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    public UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Welcome to the Admin Dashboard!";
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/all-users/{role}/{pageNumber}")
    public ResponseEntity<?> allUsers(@PathVariable int pageNumber, @PathVariable String role) {
        List<UserDTO> users = userService.findAllByRole(pageNumber, role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("Deleted User", HttpStatus.OK);
    }
}