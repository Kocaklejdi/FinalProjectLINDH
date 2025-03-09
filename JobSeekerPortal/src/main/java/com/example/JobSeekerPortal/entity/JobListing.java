package com.example.JobSeekerPortal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class JobListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private Long minPay;
    private Long maxPay;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User user;

    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private List<JobApplication> jobApplications;

    public JobListing(String title, String description, String location, Long minPay, Long maxPay, List<JobApplication> jobApplications) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.minPay = minPay;
        this.maxPay = maxPay;
        this.jobApplications = jobApplications;
    }

    public JobListing(Long id, String title, String description, String location, Long minPay, Long maxPay, List<JobApplication> jobApplications) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.minPay = minPay;
        this.maxPay = maxPay;
        this.jobApplications = jobApplications;
    }

    public JobListing() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getMinPay() {
        return minPay;
    }

    public void setMinPay(Long minPay) {
        this.minPay = minPay;
    }

    public Long getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(Long maxPay) {
        this.maxPay = maxPay;
    }

    public List<JobApplication> getApplicants() {
        return this.jobApplications;
    }

    public void setApplicants(List<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
