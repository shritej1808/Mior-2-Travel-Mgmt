package com.example.Travel_mgmt_minor.Repository;

import com.example.Travel_mgmt_minor.Entity.TravelPackage;
import com.example.Travel_mgmt_minor.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {

    List<TravelPackage> findByCompany(User company);
}
