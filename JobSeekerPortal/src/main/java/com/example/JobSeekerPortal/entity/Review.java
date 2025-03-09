package com.example.JobSeekerPortal.entity;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long rating;

    @OneToOne
    private JobListing jobListing;

    @OneToOne
    private User user;

    public Review() {
    }

    public Review(Long id, String title, String description, Long rating, User user, JobListing jobListing) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.user = user;
        this.jobListing = jobListing;
    }

    public Review(String title, String description, Long rating, User user, JobListing jobListing) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.user = user;
        this.jobListing = jobListing;
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

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JobListing getJobListing() {
        return jobListing;
    }

    public void setJobListing(JobListing jobListing) {
        this.jobListing = jobListing;
    }
}
