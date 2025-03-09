package com.example.JobSeekerPortal.repository;

import com.example.JobSeekerPortal.entity.JobListing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobListingRepository extends PagingAndSortingRepository<JobListing,Long> {

    List<JobListing> findAll();

    @Query(value = "select * from job_listing j WHERE j.posted_by = ?1", nativeQuery = true)
    List<JobListing> findByEmployerId(Long employerId, Pageable pageable);

    @Query(value = "select * from job_listing j WHERE j.posted_by = ?1 AND j.title = ?2", nativeQuery = true)
    List<JobListing> findByEmployerIdAndJobTitle(Long employerId, String jobTitle, Pageable pageable);

    @Query(value = "select * from job_listing j WHERE j.posted_by = ?1 AND j.location = ?2", nativeQuery = true)
    List<JobListing> findByEmployerIdAndLocation(Long id, String location, Pageable pageable);

    @Query(value = "select * from job_listing j WHERE j.posted_by = ?1 AND j.location = ?2 and j.title = ?3", nativeQuery = true)
    List<JobListing> findByEmployerIdLocationAndTitle(Long id, String location, String title, Pageable pageable);

    List<JobListing> findByLocation(String location, Pageable pageable);

    List<JobListing> findByLocationAndTitle(String location, String title, Pageable pageable);

    List<JobListing> findByTitle(String jobTitle, Pageable pageable);

    void save(JobListing jobListing);

    Optional<JobListing> findById(Long aLong);

    void deleteById(Long aLong);
}
