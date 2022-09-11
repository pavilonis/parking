package com.example.parking.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class NumberPlateRepository {

   private final NamedParameterJdbcTemplate jdbc;

   public void addPlateNumbers(int parkingLotId, int companyId, String numberPlate) {

      var sql = "INSERT INTO PARKING_LOT_COMPANY_NUMBER_PLATES (parking_lot_id, company_id, number_plate) \n" +
            "VALUES(:parkingLotId, :companyId, :numberPlate)";

      Map<String, Object> params = Map.of(
            "parkingLotId", parkingLotId,
            "companyId", companyId,
            "numberPlate", numberPlate
      );

      jdbc.update(sql, params);
   }

   /**
    * @return number of deleted records
    */
   public int removePlateNumbers(int parkingLotId, int companyId, String numberPlate) {
      var sql = "DELETE FROM PARKING_LOT_COMPANY_NUMBER_PLATES \n" +
            "WHERE parking_lot_id = :parkingLotId \n" +
            "  AND company_id = :companyId \n" +
            "  AND number_plate = :numberPlate";

      Map<String, Object> params = Map.of(
            "parkingLotId", parkingLotId,
            "companyId", companyId,
            "numberPlate", numberPlate
      );

      return jdbc.update(sql, params);
   }
}
