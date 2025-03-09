package com.example.JobSeekerPortal.dto;

import com.example.JobSeekerPortal.entity.User;
import com.example.JobSeekerPortal.enums.UserRoles;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Set<UserRoles> roles;
    private String email;
    private boolean enabled;
    private String verificationToken;

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO() {}

    public UserDTO(String username, String password, Set<UserRoles> roles, String email, boolean enabled) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.enabled = enabled;
    }

    public UserDTO(Long id, String username, String password, Set<UserRoles> roles, String email, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.enabled = enabled;
    }
}
