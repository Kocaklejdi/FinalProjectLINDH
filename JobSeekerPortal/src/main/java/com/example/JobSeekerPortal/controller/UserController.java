package com.example.JobSeekerPortal.controller;

import com.example.JobSeekerPortal.dto.JobApplicationDTO;
import com.example.JobSeekerPortal.dto.JobListingDTO;
import com.example.JobSeekerPortal.entity.JobApplication;
import com.example.JobSeekerPortal.mapper.JobApplicationMapper;
import com.example.JobSeekerPortal.mapper.JobListingMapper;
import com.example.JobSeekerPortal.service.ResumeService;
import com.example.JobSeekerPortal.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ResumeService resumeService;

    public UserController(UserService userService, ResumeService resumeService) {
        this.userService = userService;
        this.resumeService = resumeService;
    }

    @GetMapping("/dashboard")
    public String userDashboard() {
        return "Welcome to the User Dashboard!";
    }


    @GetMapping(value = "/jobs/{pageNumber}")
    public ResponseEntity<?> getJobs(Authentication authentication,@PathVariable int pageNumber, @RequestParam(required = false) String jobTitle, @RequestParam(required = false) String location ) {
        List<JobListingDTO> jobListings = userService.getFilteredJobs(location,jobTitle,pageNumber).stream().map(jobListing -> JobListingMapper.toJobListingDTO(jobListing)).collect(Collectors.toList());
        return new ResponseEntity<>(jobListings,HttpStatus.OK);
    }

    @GetMapping("/appliedJobs/{pageNumber}")
    public ResponseEntity<?> getAppliedJobs(Authentication authentication, @PathVariable int pageNumber, @RequestParam(required = false) String jobTitle, @RequestParam(required = false) String status ) {
        List<JobApplication> jobApplications = userService.getApplicationsByUserId(pageNumber, authentication, jobTitle, status);
        List<JobApplicationDTO> jobApplicationDTOS = jobApplications.stream().map(jobApplication -> JobApplicationMapper.toJobApplicationDTO(jobApplication)).collect(Collectors.toList());
        return new ResponseEntity<>(jobApplicationDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/upload-resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadResume(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty!");
            }
            resumeService.uploadResume(userId, file);
            return ResponseEntity.ok("Resume uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
    }
}
