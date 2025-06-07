package com.example.Travel_mgmt_minor.Controller;

import com.example.Travel_mgmt_minor.Entity.TravelPackage;
import com.example.Travel_mgmt_minor.Entity.User;
import com.example.Travel_mgmt_minor.Repository.TravelPackageRepository;
import com.example.Travel_mgmt_minor.Repository.UserRepository;
import com.example.Travel_mgmt_minor.dto.TravelPackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/packages")
public class TravelPackageController {

    @Autowired
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    private UserRepository userRepository;

    // GET all packages — shown to everyone
    @GetMapping("")
    public List<TravelPackageDTO> getAllPackages() {
        List<TravelPackage> packages = travelPackageRepository.findAll();
        return packages.stream().map(pkg -> {
            TravelPackageDTO dto = new TravelPackageDTO();
            dto.setId(pkg.getId());
            dto.setName(pkg.getName());
            dto.setDescription(pkg.getDescription());
            dto.setPrice(pkg.getPrice());
            dto.setImageUrl(pkg.getImageUrl()); // Added imageUrl in DTO output
            if (pkg.getCompany() != null) {
                dto.setCompanyName(pkg.getCompany().getUsername());
            }
            return dto;
        }).toList();
    }

    // POST: Add new package — only for travel companies, accepts DTO with imageUrl
    @PostMapping("")
    public ResponseEntity<?> addPackage(@RequestBody TravelPackageDTO dto, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        User user = userOpt.get();

        boolean isCompany = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("TRAVEL_COMPANY"));

        if (!isCompany) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only travel companies can add packages.");
        }

        TravelPackage pkg = new TravelPackage();
        pkg.setName(dto.getName());
        pkg.setDescription(dto.getDescription());
        pkg.setPrice(dto.getPrice());
        pkg.setImageUrl(dto.getImageUrl());  // set imageUrl from DTO
        pkg.setCompany(user);

        TravelPackage savedPackage = travelPackageRepository.save(pkg);
        return ResponseEntity.ok(savedPackage);
    }

    // PUT: Update package — only by its owner company
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePackage(@PathVariable Long id, @RequestBody TravelPackageDTO dto, Authentication authentication) {
        Optional<TravelPackage> existingOpt = travelPackageRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Package not found");
        }

        TravelPackage existing = existingOpt.get();
        String username = authentication.getName();

        if (!existing.getCompany().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own packages");
        }

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setImageUrl(dto.getImageUrl());  // update imageUrl too

        TravelPackage saved = travelPackageRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    // DELETE: Delete package — only by its owner company
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Long id, Authentication authentication) {
        Optional<TravelPackage> existingOpt = travelPackageRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Package not found");
        }

        TravelPackage existing = existingOpt.get();
        String username = authentication.getName();

        if (!existing.getCompany().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own packages");
        }

        travelPackageRepository.deleteById(id);
        return ResponseEntity.ok("Package deleted successfully");
    }
}
