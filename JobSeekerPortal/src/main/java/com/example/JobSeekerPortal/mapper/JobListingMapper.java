package com.example.JobSeekerPortal.mapper;

import com.example.JobSeekerPortal.dto.JobListingDTO;
import com.example.JobSeekerPortal.entity.JobListing;
import com.example.JobSeekerPortal.entity.User;

public class JobListingMapper {
    public static JobListingDTO toJobListingDTO(JobListing jobListing) {
        JobListingDTO jobListingDTO = new JobListingDTO();
        jobListingDTO.setDescription(jobListing.getDescription());
        jobListingDTO.setLocation(jobListing.getLocation());
        jobListingDTO.setMaxPay(jobListing.getMaxPay());
        jobListingDTO.setMinPay(jobListing.getMinPay());
        jobListingDTO.setTitle(jobListing.getTitle());
        return jobListingDTO;
    }
    public static JobListing toJobListing(JobListingDTO jobListingDTO) {
        JobListing jobListing = new JobListing();
        jobListing.setDescription(jobListingDTO.getDescription());
        jobListing.setLocation(jobListingDTO.getLocation());
        jobListing.setMaxPay(jobListingDTO.getMaxPay());
        jobListing.setMinPay(jobListingDTO.getMinPay());
        jobListing.setTitle(jobListingDTO.getTitle());
        jobListing.setUser(UserMapper.toUser(jobListingDTO.getUserDTO()));
        return jobListing;
    }
}
