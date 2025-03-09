package com.example.JobSeekerPortal.mapper;

import com.example.JobSeekerPortal.dto.ReviewDTO;
import com.example.JobSeekerPortal.entity.Review;

public class ReviewMapper {

    public ReviewMapper() {}

    public static Review toReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setDescription(reviewDTO.getDescription());
        review.setRating(reviewDTO.getRating());
        review.setTitle(reviewDTO.getTitle());
        return review;
    }
    public static ReviewDTO toReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setDescription(review.getDescription());
        reviewDTO.setPostedBy(review.getUser().getUsername());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setTitle(review.getTitle());
        reviewDTO.setJobListingDTO(JobListingMapper.toJobListingDTO(review.getJobListing()));
        return reviewDTO;
    }
}
