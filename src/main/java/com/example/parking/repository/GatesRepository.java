package com.example.parking.repository;

import com.example.parking.domain.ParkingEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

   public void saveEvent(int parkingLotId, String numberPlate, ParkingEventType type) {
      Map<String, Object> params = Map.of(
            "parkingLotId", parkingLotId,
            "numberPlate", numberPlate,
            "eventType", type.name()
      );
      jdbc.update("INSERT INTO PARKING_EVENT (parking_lot_id, number_plate, event_type) \n" +
            "VALUES (:parkingLotId, :numberPlate, :eventType)", params);
   }

   public Map<String, Integer> loadNumberPlateUsages() {
      Map<String, Integer> result = new HashMap<>();
      Map<String, String> params = Map.of("typeEntrance", ParkingEventType.ENTRANCE.name());
      var sql = "SELECT number_plate, COUNT(*) AS number \n" +
            "FROM PARKING_EVENT " +
            "WHERE event_type = :typeEntrance \n" +
            "GROUP BY number_plate";

      jdbc.query(sql, params, (rs, i) -> result.put(rs.getString("number_plate"), rs.getInt("number")));
      return result;
   }

   public Map<String, Map<String, Set<String>>> loadCompanyActiveParkings() {
      Map<String, Map<String, Set<String>>> result = new HashMap<>();
      var sql = "SELECT " +
            "  c.name AS company, " +
            "  p.name AS parking, " +
            "  e1.number_plate AS plate \n" +
            "FROM PARKING_EVENT e1 \n" +

            "  INNER JOIN ( \n" +
            "     SELECT e2.number_plate AS numbers, MAX(e2.created) AS created \n" +
            "     FROM PARKING_EVENT e2 \n" +
            "     GROUP BY e2.number_plate \n" +
            "  ) lastEvent ON lastEvent.numbers = e1.number_plate AND lastEvent.created = e1.created \n" +

            "  INNER JOIN PARKING_LOT_COMPANY_NUMBER_PLATES com_plate ON com_plate.number_plate = e1.number_plate \n" +
            "  INNER JOIN COMPANY c ON c.id = com_plate.company_id \n" +
            "  INNER JOIN PARKING_LOT p ON p.id = com_plate.parking_lot_id \n" +

            "WHERE e1.event_type = :typeEntrance";
      jdbc.query(sql, Map.of("typeEntrance", ParkingEventType.ENTRANCE.name()), (rs) -> {
         result.computeIfAbsent(rs.getString("company"), c -> new HashMap<>())
               .computeIfAbsent(rs.getString("parking"), p -> new HashSet<>())
               .add(rs.getString("plate"));
      });
      return result;
   }
}
