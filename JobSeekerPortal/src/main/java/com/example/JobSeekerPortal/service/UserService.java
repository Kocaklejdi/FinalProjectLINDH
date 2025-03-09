package com.example.JobSeekerPortal.service;

import com.example.JobSeekerPortal.dto.EmailRecepientDTO;
import com.example.JobSeekerPortal.dto.UserDTO;
import com.example.JobSeekerPortal.entity.JobApplication;
import com.example.JobSeekerPortal.entity.JobListing;
import com.example.JobSeekerPortal.entity.User;
import com.example.JobSeekerPortal.enums.ApplicationStatus;
import com.example.JobSeekerPortal.enums.UserRoles;
import com.example.JobSeekerPortal.mapper.UserMapper;
import com.example.JobSeekerPortal.repository.JobApplicationRepository;
import com.example.JobSeekerPortal.repository.JobListingRepository;
import com.example.JobSeekerPortal.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobListingRepository jobListingRepository;

    public UserService(UserRepository userRepository, JobListingRepository jobListingRepository, JobApplicationRepository jobApplicationRepository, ResumeService resumeService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.jobListingRepository = jobListingRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public Optional<User> verifyToken(String token) {
        Optional<User> user = userRepository.findByVerificationToken(token);

        if (user.isPresent()) {
            user.get().setEnabled(true);
            user.get().setVerificationToken(null);
            userRepository.save(user.get());
            return user;
        }
        return Optional.empty();
    }

    public List<UserDTO> findAllByRole(int pageNumber, String role) {
        Pageable request = PageRequest.of(pageNumber, 10);
        return userRepository.findAllByRole(role,request).stream().map(user->UserMapper.toUserDTO(user)).collect(Collectors.toList());
    }

    public List<JobApplication> getApplicationsByUserId(int pageNumber,Authentication authentication, String jobTitle, String status) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        Pageable request = PageRequest.of(pageNumber, 10);
        if(jobTitle != null && status != null) {
            return jobApplicationRepository.findByApplicantIdAndStatusAndJobTitle(user.getId(),ApplicationStatus.valueOf(status),jobTitle,request);
        } else if(jobTitle != null && status == null) {
            return jobApplicationRepository.findByApplicantIdAndJobTitle(user.getId(), jobTitle, request);
        }else if(jobTitle == null && status != null) {
            return jobApplicationRepository.findByApplicantIdAndStatus(user.getId(), ApplicationStatus.valueOf(status),request);
        }else{
            return jobApplicationRepository.findByApplicantId(user.getId(), request);
        }
    }

    public List<UserDTO> findAllUsers(){
        return userRepository.findAll().stream().map(user->UserMapper.toUserDTO(user)).collect(Collectors.toList());
    }

    public EmailRecepientDTO addUser(UserDTO userDTO) {
        User user = UserMapper.toUser(userDTO);
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        String token = UUID.randomUUID().toString();

        user.setEnabled(false);
        user.setVerificationToken(token);
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        EmailRecepientDTO emailRecepientDTO = new EmailRecepientDTO();

        emailRecepientDTO.setEmail(user.getEmail());
        emailRecepientDTO.setToken(token);

        return emailRecepientDTO;
    }

    public Optional<UserDTO> findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return Optional.of(UserMapper.toUserDTO(user.get()));
        }else {
            return Optional.empty();
        }
    }

    public List<JobListing> getFilteredJobs(String location, String jobTitle, int pageNumber) {
        Pageable request = PageRequest.of(pageNumber, 10);
        if(location != null && jobTitle != null) {
            return jobListingRepository.findByLocationAndTitle(location, jobTitle, request);
        }else if(location == null && jobTitle != null) {
            return jobListingRepository.findByTitle(jobTitle, request);
        }else if(location != null && jobTitle == null) {
            return jobListingRepository.findByLocation(location, request);
        }
        return jobListingRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRoles().stream().map(userRoles -> userRoles.name()).collect(Collectors.toSet()))
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<JobListing> getAllJobListings() {
        return jobListingRepository.findAll();
    }

    public boolean isUserEnabledByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().isEnabled();
        }else {
            return false;
        }
    }

}
