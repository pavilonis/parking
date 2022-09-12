package com.example.parking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import java.util.Map;

@Getter
@Setter
@Entity
public class ParkingLot extends BaseEntity {

   private int size;

   @JsonIgnore
   @ElementCollection
   @CollectionTable(name = "PARKING_LOT_COMPANY_SPACES", joinColumns = @JoinColumn(name = "parking_lot_id"))
   @MapKeyJoinColumn(name = "company_id")
   @Column(name = "spaces")
   private Map<Company, Integer> companySpaceReservations;

}
