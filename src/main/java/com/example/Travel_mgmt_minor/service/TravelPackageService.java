package com.example.Travel_mgmt_minor.service;

//package com.example.Travel_mgmt_minor.Service;

import com.example.Travel_mgmt_minor.Entity.TravelPackage;
import com.example.Travel_mgmt_minor.Repository.TravelPackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelPackageService {

    private final TravelPackageRepository repository;

    public TravelPackageService(TravelPackageRepository repository) {
        this.repository = repository;
    }

    public List<TravelPackage> getAllPackages() {
        return repository.findAll();
    }

    public TravelPackage createPackage(TravelPackage travelPackage) {
        return repository.save(travelPackage);
    }
}
