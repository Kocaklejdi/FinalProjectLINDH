package com.example.JobSeekerPortal.controller;

import com.example.JobSeekerPortal.dto.ReviewDTO;
import com.example.JobSeekerPortal.service.ReviewService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final ReviewService reviewService;

    public PublicController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/reviews/{page}")
    public ResponseEntity<List<ReviewDTO>> reviews(@PathVariable int page, @RequestParam(required = false) String sorted) {
        return new ResponseEntity<>(reviewService.getAllReviewsSorted(page, sorted),HttpStatus.OK);
    }
}
