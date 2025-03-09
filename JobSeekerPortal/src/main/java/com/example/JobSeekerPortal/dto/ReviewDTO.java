package com.example.JobSeekerPortal.dto;

public class ReviewDTO {
    private String title;
    private String description;
    private Long rating;
    private String postedBy;
    private JobListingDTO jobListingDTO;

    public JobListingDTO getJobListingDTO() {
        return jobListingDTO;
    }

    public void setJobListingDTO(JobListingDTO jobListingDTO) {
        this.jobListingDTO = jobListingDTO;
    }

    public ReviewDTO() {}
    public ReviewDTO(String title, String description, Long rating) {}

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
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
}
