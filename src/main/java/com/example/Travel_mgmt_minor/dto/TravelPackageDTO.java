package com.example.Travel_mgmt_minor.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelPackageDTO {
    private Long id;
    private String name;
    private String description;
    private String companyName;
    private Double price;
    private String imageUrl;
// <-- add this line
}


