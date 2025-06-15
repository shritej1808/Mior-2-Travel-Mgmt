package com.example.Travel_mgmt_minor.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TravelPackageDTO {
    private Long id;
    private String name;
    private String description;
    private String companyName;
    private Double price;
    private String imageUrl;
    private Double averageRating;
    private Integer reviewCount;  // New field
    private List<ReviewDTO> reviews;
}