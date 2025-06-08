package com.example.Travel_mgmt_minor.Controller;

import com.example.Travel_mgmt_minor.Entity.Booking;
import com.example.Travel_mgmt_minor.Entity.TravelPackage;
import com.example.Travel_mgmt_minor.Entity.User;
import com.example.Travel_mgmt_minor.Repository.BookingRepository;
import com.example.Travel_mgmt_minor.Repository.TravelPackageRepository;
import com.example.Travel_mgmt_minor.Repository.UserRepository;
import com.example.Travel_mgmt_minor.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/payment")
@PreAuthorize("hasAuthority('USER')")
public class PaymentController {

    @Autowired private TravelPackageRepository packageRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private EmailService emailService;

    @GetMapping("/{packageId}")
    public ResponseEntity<String> showPaymentPage(@PathVariable Long packageId) {
        return ResponseEntity.ok("Select payment method and click confirm to finalize booking for package ID: " + packageId);
    }

    @PostMapping("/{packageId}/confirm")
    public ResponseEntity<String> confirmBooking(@PathVariable Long packageId, Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        TravelPackage travelPackage = packageRepo.findById(packageId).orElseThrow();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTravelPackage(travelPackage);
        booking.setBookingTime(LocalDateTime.now());
        bookingRepo.save(booking);

        emailService.sendBookingConfirmation(user.getEmail(), user.getUsername(), travelPackage.getName());

        return ResponseEntity.ok("Booking confirmed and email sent.");
    }
}
