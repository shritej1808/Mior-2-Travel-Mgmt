
package com.example.Travel_mgmt_minor.Repository;

import com.example.Travel_mgmt_minor.Entity.Review;
import com.example.Travel_mgmt_minor.Entity.TravelPackage;
import com.example.Travel_mgmt_minor.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTravelPackage(TravelPackage travelPackage);
    boolean existsByUserAndTravelPackage(User user, TravelPackage travelPackage);
}
