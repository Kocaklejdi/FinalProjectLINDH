package com.example.JobSeekerPortal.service;

import com.example.JobSeekerPortal.entity.JobApplication;
import com.example.JobSeekerPortal.entity.JobListing;
import com.example.JobSeekerPortal.entity.User;
import com.example.JobSeekerPortal.enums.ApplicationStatus;
import com.example.JobSeekerPortal.repository.JobApplicationRepository;
import com.example.JobSeekerPortal.repository.JobListingRepository;
import com.example.JobSeekerPortal.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class JobApplicationService {
    private final JobListingRepository jobListingRepository;
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationService(JobListingRepository jobListingRepository, UserRepository userRepository, JobApplicationRepository jobApplicationRepository) {
        this.jobListingRepository = jobListingRepository;
        this.userRepository = userRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public JobApplication applyToJob(Long jobId, Authentication authentication) {
        Optional<User> user = userRepository.findByUsername(authentication.getName());

        Optional<JobListing> job = jobListingRepository.findById(jobId);

        JobApplication application = new JobApplication();
        application.setApplicant(user.get());
        application.setJob(job.get());
        application.setStatus(ApplicationStatus.PENDING);
        application.setAppliedAt(LocalDateTime.now());

        return jobApplicationRepository.save(application);
    }
}
