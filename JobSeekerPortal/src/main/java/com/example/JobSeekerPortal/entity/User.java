package com.example.JobSeekerPortal.entity;

import com.example.JobSeekerPortal.enums.UserRoles;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;

    @Column(unique = true, nullable = false)
    private String email;
    private boolean enabled;

    private String verificationToken;

    private String resumePath;

    @OneToMany(mappedBy = "applicant")
    private List<JobApplication> appliedJobs;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<UserRoles> roles;

    public User(Long id, String username, String password,String email, Set<UserRoles> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }

    public User() {
    }

    public User(String username, String password,String email ,Set<UserRoles> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }

    public List<JobApplication> getAppliedJobs() {
        return appliedJobs;
    }

    public void setAppliedJobs(List<JobApplication> appliedJobs) {
        this.appliedJobs = appliedJobs;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
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

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
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

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}

