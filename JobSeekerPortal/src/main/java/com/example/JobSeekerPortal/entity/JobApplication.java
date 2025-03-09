package com.example.JobSeekerPortal.entity;

import com.example.JobSeekerPortal.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_id"}))
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User applicant; // The user applying for the job

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private JobListing job;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime appliedAt;

    public JobApplication(User applicant, JobListing job, ApplicationStatus status, LocalDateTime appliedAt) {
        this.applicant = applicant;
        this.job = job;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public JobApplication(Long id, User applicant, JobListing job, ApplicationStatus status, LocalDateTime appliedAt) {
        this.id = id;
        this.applicant = applicant;
        this.job = job;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public JobApplication() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public JobListing getJob() {
        return job;
    }

    public void setJob(JobListing job) {
        this.job = job;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}