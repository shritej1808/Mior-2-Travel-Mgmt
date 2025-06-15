package com.example.Travel_mgmt_minor.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class TravelPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private User company;

    // Add this relationship
    @OneToMany(mappedBy = "travelPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}