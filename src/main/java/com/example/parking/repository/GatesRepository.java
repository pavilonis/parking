package com.example.parking.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class GatesRepository {

   private final NamedParameterJdbcTemplate jdbc;

   public boolean isNumberPlateValid(int parkingLotId, String numberPlate) {

      Map<String, Object> params = Map.of("parkingLotId", parkingLotId, "numberPlate", numberPlate);
      String sql = "SELECT EXISTS( \n" +
            "  SELECT 1 \n" +
            "  FROM PARKING_LOT_COMPANY_NUMBER_PLATES \n" +
            "  WHERE parking_lot_id = :parkingLotId AND number_plate = :numberPlate \n" +
            ")";

      Boolean result = jdbc.queryForObject(sql, params, Boolean.class);
      return Boolean.TRUE == result;
   }
}
