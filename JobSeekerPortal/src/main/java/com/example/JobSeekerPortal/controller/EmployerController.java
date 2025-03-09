package com.example.JobSeekerPortal.controller;

import com.example.JobSeekerPortal.dto.JobListingDTO;
import com.example.JobSeekerPortal.dto.ReviewDTO;
import com.example.JobSeekerPortal.dto.StatusDTO;
import com.example.JobSeekerPortal.dto.UserDTO;
import com.example.JobSeekerPortal.entity.User;
import com.example.JobSeekerPortal.mapper.JobListingMapper;
import com.example.JobSeekerPortal.mapper.UserMapper;
import com.example.JobSeekerPortal.service.EmployerService;
import com.example.JobSeekerPortal.service.ResumeService;
import com.example.JobSeekerPortal.service.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    private final EmployerService employerService;
    private final UserService userService;
    private final ResumeService resumeService;

    public EmployerController(EmployerService employerService, UserService userService, ResumeService resumeService) {
        this.employerService = employerService;
        this.userService = userService;
        this.resumeService = resumeService;
    }

    @GetMapping(value = "/jobs")
    public ResponseEntity<?> getJobs() {
        List<JobListingDTO> jobListings = employerService.getAllJobListings().stream().map(jobListing -> JobListingMapper.toJobListingDTO(jobListing)).collect(Collectors.toList());
        return new ResponseEntity<>(jobListings,HttpStatus.OK);
    }

    @PutMapping("/update/{jobID}/{applicantID}")
    public ResponseEntity<String> updateApplication(@PathVariable Long jobID, @PathVariable Long applicantID, @RequestBody StatusDTO status, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(employerService.updateApplication(jobID, applicantID, status, authentication));
    }

    @GetMapping("/applicantsToJob/{jobId}")
    public ResponseEntity<?> getApplicantsToJob(@PathVariable Long jobId) {
        List<User> users = employerService.getAllUsersWhoAppliedToJobListing(jobId);
        List<UserDTO> userDTOS = users.stream().map(user -> UserMapper.toUserDTO(user)).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/jobs/{employerId}/{pageNumber}")
    public ResponseEntity<?> getJobsWithFilters(@PathVariable Long employerId,@PathVariable int pageNumber,@RequestBody JobListingDTO jobListingDTO) {
        String location = jobListingDTO.getLocation();
        String title = jobListingDTO.getTitle();
        List<JobListingDTO> jobListings = employerService.getFilteredJobs(employerId, location, title, pageNumber).stream().map(jobListing -> JobListingMapper.toJobListingDTO(jobListing)).collect(Collectors.toList());
        return new ResponseEntity<>(jobListings,HttpStatus.OK);
    }

    @PostMapping(value = "/jobs/review/{jobId}")
    public ResponseEntity<String> reviewJob(@PathVariable Long jobId, @RequestBody ReviewDTO reviewDTO, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(employerService.addReview(jobId,reviewDTO,authentication));
    }

    @PostMapping("/new")
    ResponseEntity<?> addNewJob(@RequestBody JobListingDTO jobListingDTO, Authentication authentication) {
        employerService.save(jobListingDTO, authentication);
        return new ResponseEntity<>(jobListingDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/download-resume", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> downloadResume(@PathVariable Long userId, Authentication authentication) {
            FileSystemResource file = resumeService.getResume(userId);
            if (file != null) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(file);
            }
        return ResponseEntity.notFound().build();
    }
}
