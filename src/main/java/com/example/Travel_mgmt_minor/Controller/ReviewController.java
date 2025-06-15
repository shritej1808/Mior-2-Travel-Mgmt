package com.example.Travel_mgmt_minor.Controller;

import com.example.Travel_mgmt_minor.Entity.Review;
import com.example.Travel_mgmt_minor.Entity.TravelPackage;
import com.example.Travel_mgmt_minor.Entity.User;
import com.example.Travel_mgmt_minor.Repository.ReviewRepository;
import com.example.Travel_mgmt_minor.Repository.TravelPackageRepository;
import com.example.Travel_mgmt_minor.Repository.UserRepository;
import com.example.Travel_mgmt_minor.dto.ReviewDTO;
import com.example.Travel_mgmt_minor.dto.TravelPackageDTO;
import com.example.Travel_mgmt_minor.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO reviewDTO,
                                       Authentication authentication) {
        try {
            // Validate input
            if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
                return ResponseEntity.badRequest().body("Rating must be between 1 and 5");
            }

            if (reviewDTO.getComment() == null || reviewDTO.getComment().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Comment cannot be empty");
            }

            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            TravelPackage travelPackage = travelPackageRepository.findById(reviewDTO.getPackageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

            // Check if user already reviewed this package
            if (reviewRepository.existsByUserAndTravelPackage(user, travelPackage)) {
                return ResponseEntity.badRequest().body("You've already reviewed this package");
            }

            Review review = new Review();
            review.setTravelPackage(travelPackage);
            review.setUser(user);
            review.setRating(reviewDTO.getRating());
            review.setComment(reviewDTO.getComment().trim());
            review.setCreatedAt(LocalDateTime.now());

            Review savedReview = reviewRepository.save(review);

            // Return the complete package with updated reviews
            TravelPackage updatedPackage = travelPackageRepository.findById(reviewDTO.getPackageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

            return ResponseEntity.ok(convertToDTO(updatedPackage));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to submit review: " + e.getMessage());
        }
    }

    @GetMapping("/package/{packageId}")
    public ResponseEntity<List<Review>> getReviewsByPackage(@PathVariable Long packageId) {
        TravelPackage travelPackage = travelPackageRepository.findById(packageId)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        List<Review> reviews = reviewRepository.findByTravelPackage(travelPackage);
        return ResponseEntity.ok(reviews);
    }

    private TravelPackageDTO convertToDTO(TravelPackage pkg) {
        TravelPackageDTO dto = new TravelPackageDTO();
        dto.setId(pkg.getId());
        dto.setName(pkg.getName());
        dto.setDescription(pkg.getDescription());
        dto.setPrice(pkg.getPrice());
        dto.setImageUrl(pkg.getImageUrl());

        if (pkg.getCompany() != null) {
            dto.setCompanyName(pkg.getCompany().getUsername());
        }

        if (pkg.getReviews() != null) {
            dto.setReviewCount(pkg.getReviews().size());

            if (!pkg.getReviews().isEmpty()) {
                double average = pkg.getReviews().stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);
                dto.setAverageRating(average);
            }
        }

        return dto;
    }
}