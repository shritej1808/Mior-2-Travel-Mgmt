package com.example.Travel_mgmt_minor.Repository;

import com.example.Travel_mgmt_minor.Entity.Booking;
import com.example.Travel_mgmt_minor.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}

