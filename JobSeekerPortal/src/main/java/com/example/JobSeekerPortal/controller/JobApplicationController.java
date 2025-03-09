package com.example.JobSeekerPortal.controller;

import com.example.JobSeekerPortal.entity.JobApplication;
import com.example.JobSeekerPortal.service.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<JobApplication> applyToJob(@PathVariable Long jobId, Authentication authentication) {
        JobApplication application = jobApplicationService.applyToJob(jobId, authentication);
        return ResponseEntity.ok(application);
    }
}
