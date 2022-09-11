package com.example.parking.controller;

import com.example.parking.domain.Company;
import com.example.parking.domain.ParkingLot;
import com.example.parking.repository.CompanyCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RequestMapping("api/monitoring")
@RequiredArgsConstructor
@RestController
public class MonitoringController {

   private final CompanyCrudRepository companyRepository;

   @RequestMapping("/companies")
   @GetMapping
   public Map<String, Map<String, Integer>> loadAll() {
      List<Company> companies = companyRepository.findAll();
      return companies.stream().collect(toMap(
            Company::getName,
            company -> formatParkingLotSpaces(company.getParkingLotSpaces())
      ));
   }

   private Map<String, Integer> formatParkingLotSpaces(Map<ParkingLot, Integer> parkingLotSpaces) {
      Map<String, Integer> result = new HashMap<>();
      parkingLotSpaces.forEach((lot, spaces) -> result.put(lot.getName(), spaces));
      return result;
   }
}
