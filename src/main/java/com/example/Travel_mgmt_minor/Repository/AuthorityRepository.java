package com.example.Travel_mgmt_minor.Repository;



import com.example.Travel_mgmt_minor.Entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority(String authority);
}
