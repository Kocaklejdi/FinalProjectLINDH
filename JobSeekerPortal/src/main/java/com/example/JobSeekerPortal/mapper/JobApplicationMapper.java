package com.example.JobSeekerPortal.mapper;

import com.example.JobSeekerPortal.dto.JobApplicationDTO;
import com.example.JobSeekerPortal.entity.JobApplication;

public class JobApplicationMapper {
    public static JobApplicationDTO toJobApplicationDTO(JobApplication jobApplication) {
        JobApplicationDTO jobApplicationDTO = new JobApplicationDTO();
        jobApplicationDTO.setJobListingDTO(JobListingMapper.toJobListingDTO(jobApplication.getJob()));
        jobApplicationDTO.setStatus(jobApplication.getStatus());
        jobApplicationDTO.setAppliedAt(jobApplication.getAppliedAt());
        return jobApplicationDTO;
    }

    public static JobApplication toJobApplication(JobApplicationDTO jobApplicationDTO) {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setStatus(jobApplicationDTO.getStatus());
        jobApplication.setAppliedAt(jobApplicationDTO.getAppliedAt());
        jobApplication.setJob(JobListingMapper.toJobListing(jobApplicationDTO.getJobListingDTO()));
        return jobApplication;
    }
}
