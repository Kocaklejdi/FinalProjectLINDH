package com.example.JobSeekerPortal.service;

import com.example.JobSeekerPortal.dto.ReviewDTO;
import com.example.JobSeekerPortal.entity.Review;
import com.example.JobSeekerPortal.mapper.ReviewMapper;
import com.example.JobSeekerPortal.repository.ReviewRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDTO> getAllReviewsSorted(int page,String sorted) {
        Pageable pageable = PageRequest.of(page, 10);
        if(sorted != null) {
            return reviewRepository.findAllByOrderByRatingDesc(pageable).stream().map(review -> ReviewMapper.toReviewDTO(review)).collect(Collectors.toList());
        }
       return reviewRepository.findReviews(pageable).stream().map(review -> ReviewMapper.toReviewDTO(review)).collect(Collectors.toList());
    }
}
