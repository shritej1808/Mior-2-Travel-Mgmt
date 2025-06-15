package com.example.Travel_mgmt_minor.Repository;

import com.example.Travel_mgmt_minor.Entity.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {

    @Query("SELECT p FROM TravelPackage p LEFT JOIN p.reviews r " +
            "GROUP BY p.id ORDER BY COALESCE(AVG(r.rating), 0) DESC")
    List<TravelPackage> findAllByOrderByAverageRatingDesc();

    @Query("SELECT p FROM TravelPackage p LEFT JOIN p.reviews r " +
            "GROUP BY p.id ORDER BY COALESCE(AVG(r.rating), 0) ASC")
    List<TravelPackage> findAllByOrderByAverageRatingAsc();

    @Query("SELECT p FROM TravelPackage p LEFT JOIN p.reviews r " +
            "GROUP BY p.id ORDER BY COUNT(r.id) DESC")
    List<TravelPackage> findAllByOrderByReviewCountDesc();

    @Query("SELECT p FROM TravelPackage p LEFT JOIN p.reviews r " +
            "GROUP BY p.id HAVING COUNT(r.id) >= :minReviews " +
            "ORDER BY COALESCE(AVG(r.rating), 0) DESC")
    List<TravelPackage> findPopularPackages(@Param("minReviews") int minReviews);
}