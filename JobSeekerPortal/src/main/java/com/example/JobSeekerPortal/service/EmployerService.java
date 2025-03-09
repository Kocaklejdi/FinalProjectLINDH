package com.example.JobSeekerPortal.service;

import com.example.JobSeekerPortal.dto.JobListingDTO;
import com.example.JobSeekerPortal.dto.ReviewDTO;
import com.example.JobSeekerPortal.dto.StatusDTO;
import com.example.JobSeekerPortal.entity.JobApplication;
import com.example.JobSeekerPortal.entity.JobListing;
import com.example.JobSeekerPortal.entity.Review;
import com.example.JobSeekerPortal.entity.User;
import com.example.JobSeekerPortal.enums.ApplicationStatus;
import com.example.JobSeekerPortal.mapper.JobListingMapper;
import com.example.JobSeekerPortal.mapper.ReviewMapper;
import com.example.JobSeekerPortal.mapper.UserMapper;
import com.example.JobSeekerPortal.repository.JobApplicationRepository;
import com.example.JobSeekerPortal.repository.JobListingRepository;
import com.example.JobSeekerPortal.repository.ReviewRepository;
import com.example.JobSeekerPortal.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployerService {
    private final UserRepository userRepository;
    private final JobListingRepository jobListingRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final ReviewRepository reviewRepository;
    private final EmailService emailService;

    public EmployerService(UserRepository userRepository, JobListingRepository jobListingRepository, JobApplicationRepository jobApplicationRepository, ReviewRepository reviewRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.jobListingRepository = jobListingRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.reviewRepository = reviewRepository;
        this.emailService = emailService;
    }

    public List<JobListing> getAllJobListings() {
        return jobListingRepository.findAll();
    }

    public List<JobListing> getFilteredJobs(Long userId, String location, String jobTitle, int pageNumber) {
        Pageable request = PageRequest.of(pageNumber, 10);
        if(location != null && jobTitle != null) {
            return jobListingRepository.findByEmployerIdLocationAndTitle(userId, location, jobTitle, request);
        }else if(location == null && jobTitle != null) {
            return jobListingRepository.findByEmployerIdAndJobTitle(userId, jobTitle, request);
        }else if(location != null && jobTitle == null) {
            return jobListingRepository.findByEmployerIdAndLocation(userId, location, request);
        }
        return jobListingRepository.findByEmployerId(userId, request);
    }

    public List<User> getAllUsersWhoAppliedToJobListing(Long userId) {
        return userRepository.findUsersWhoAppliedToJobListing(userId, Pageable.unpaged());
    }

    public void save(JobListingDTO jobListingDTO, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        jobListingDTO.setUserDTO(UserMapper.toUserDTO(user.get()));
        JobListing jobListing = JobListingMapper.toJobListing(jobListingDTO);
        jobListingRepository.save(jobListing);
    }

    public String updateApplication(Long jobID, Long applicantID, StatusDTO status, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        JobListing jobListing = jobListingRepository.findById(jobID).get();
        if(Objects.equals(user.getId(), jobListing.getUser().getId())) {
            JobApplication jobApplication = jobApplicationRepository.findByApplicantAndJob(applicantID, jobID);
            jobApplication.setStatus(ApplicationStatus.valueOf(status.getStatus()));
            jobApplicationRepository.save(jobApplication);
            emailService.sendJobUpdateEmail(user,jobListing,status.getStatus());
            return "Application updated";
        }else {
            return "Only job poster can update application";
        }
    }

    public String addReview(Long jobID, ReviewDTO reviewDTO, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        if(Objects.equals(user.getId(), jobListingRepository.findById(jobID).get().getUser().getId())) {
            Review review = ReviewMapper.toReview(reviewDTO);
            review.setUser(user);
            review.setJobListing(jobListingRepository.findById(jobID).get());
            reviewRepository.save(review);
            return "Review added";
        }else {
            return "Only job poster can review job";
        }
    }
}
