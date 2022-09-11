package com.example.parking.repository;

import com.example.parking.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface CompanyCrudRepository extends JpaRepository<Company, Integer> {
   default Company getCompany(int companyId) {
      return findById(companyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company lot not found"));
   }
}
