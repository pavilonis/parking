package com.example.parking.controller;

import com.example.parking.domain.BaseEntity;
import com.example.parking.domain.Company;
import com.example.parking.domain.ParkingLot;
import com.example.parking.repository.CompanyCrudRepository;
import com.example.parking.service.GatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@RequestMapping("api/monitoring")
@RequiredArgsConstructor
@RestController
public class MonitoringController {

   private final CompanyCrudRepository companyRepository;
   private final GatesService gatesService;

   @GetMapping("/company-spaces")
   public Map<String, Map<String, Integer>> companyBookedSpaces() {
      List<Company> companies = companyRepository.findAll();
      return companies.stream().collect(toMap(
            BaseEntity::getName,
            company -> formatParkingLotSpaces(company.getParkingLotSpaces())
      ));
   }

   @GetMapping("/number-plate-usages")
   public Map<String, Integer> loadNumberPlatesUsages() {
      return gatesService.loadNumberPlatesUsages();
   }

   @GetMapping("/company-active-parkings")
   public Map<String, Map<String, Set<String>>> companyCurrentlyParkedNumberPlates() {
      return gatesService.loadCompanyActiveParkings();
   }

   private Map<String, Integer> formatParkingLotSpaces(Map<ParkingLot, Integer> parkingLotSpaces) {
      Map<String, Integer> result = new HashMap<>();
      parkingLotSpaces.forEach((lot, spaces) -> result.put(lot.getName(), spaces));
      return result;
   }
}
