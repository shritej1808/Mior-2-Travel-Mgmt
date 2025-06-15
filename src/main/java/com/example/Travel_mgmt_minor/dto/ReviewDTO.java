package com.example.Travel_mgmt_minor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private Long packageId;
    private int rating;
    private String comment;
}