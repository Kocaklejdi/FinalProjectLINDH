package com.example.JobSeekerPortal.repository;

import com.example.JobSeekerPortal.entity.JobApplication;
import com.example.JobSeekerPortal.entity.JobListing;
import com.example.JobSeekerPortal.entity.User;
import com.example.JobSeekerPortal.enums.ApplicationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends PagingAndSortingRepository<JobApplication, Long> {
    List<JobApplication> findByApplicant(User user);
    List<JobApplication> findByJob(JobListing job);

    @Query(value = "select * from job_application j where j.user_id = ?1 and j.job_id = ?2;",nativeQuery = true)
    JobApplication findByApplicantAndJob(Long applicantID, Long jobID);


    List<JobApplication> findByApplicantIdAndJobTitle(Long userId, String jobTitle, Pageable pageable);

    List<JobApplication> findByApplicantIdAndStatus(Long id, ApplicationStatus status, Pageable pageable);

    List<JobApplication> findByApplicantIdAndStatusAndJobTitle(Long id,ApplicationStatus status, String jobTitle, Pageable pageable);

    List<JobApplication> findByApplicantId(Long applicantID, Pageable pageable);

    JobApplication save(JobApplication application);
}