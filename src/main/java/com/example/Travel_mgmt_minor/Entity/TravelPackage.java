package com.example.Travel_mgmt_minor.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageName;

    private String description;

    private double price;

    // Link to company (User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private User company;

    public String getName() {
        return packageName;
    }

    public void setName(String name) {
        this.packageName = name;
    }
    @Column(name = "image_url")
    private String imageUrl; // e.g., https://example.com/image.jpg

}
