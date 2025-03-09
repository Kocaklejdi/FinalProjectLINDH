package com.example.JobSeekerPortal.dto;

import com.example.JobSeekerPortal.enums.ApplicationStatus;

import java.time.LocalDateTime;

public class JobApplicationDTO {
    private LocalDateTime appliedAt;
    private ApplicationStatus status;
    private JobListingDTO jobListingDTO;

    public JobApplicationDTO(LocalDateTime appliedAt, ApplicationStatus status, JobListingDTO jobListingDTO) {
        this.appliedAt = appliedAt;
        this.status = status;
        this.jobListingDTO = jobListingDTO;
    }

    public JobApplicationDTO() {
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public JobListingDTO getJobListingDTO() {
        return jobListingDTO;
    }

    public void setJobListingDTO(JobListingDTO jobListingDTO) {
        this.jobListingDTO = jobListingDTO;
    }
}
