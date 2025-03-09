package com.example.JobSeekerPortal.repository;

import com.example.JobSeekerPortal.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {

    List<Review> findAllByOrderByRatingDesc(Pageable pageable);

    @Query(value = "select * from review", nativeQuery = true)
    List<Review> findReviews(Pageable pageable);

    void save(Review r);

    void delete(Review r);
}
